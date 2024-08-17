package org.kamae.fooddairy.bot.service

import jakarta.transaction.Transactional
import org.kamae.fooddairy.entity.User
import org.kamae.fooddairy.enums.ChatState
import org.kamae.fooddairy.service.GroupService
import org.kamae.fooddairy.service.PurchaseAdapter
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

interface ResponseService {
    fun handleUserResponse(update: Update, bot: TelegramLongPollingBot)
}

@Service
class ResponseServiceImpl(
    private val chatService: ChatService,
    private val purchaseAdapter: PurchaseAdapter,
    private val groupService: GroupService,
    private val botService: PurchaseBotService
) : ResponseService {
    @Transactional
    override fun handleUserResponse(update: Update, bot: TelegramLongPollingBot) {
        val chatId = update.message.chatId

        val message = update.message.text
        val chatState = chatService.getChat(chatId)?.state

        var user: User? = null
        if (chatState != ChatState.WAIT_NAME.name) {
            user = chatService.getUser(update.message.chat.userName)
        }

        when (chatState) {
            ChatState.WAIT_NAME.name -> chatService.registration(
                update.message.chat.userName,
                update.message.text,
                chatId,
                bot
            )

            ChatState.WAIT_GROUP_NAME.name -> {
                groupService.createGroup(message, user!!)
                botService.sendGroupCreatedMessage(chatId, bot)
            }

            ChatState.WAIT_POSITIONS.name -> {
                purchaseAdapter.addToPurchase(user!!, user.usedGroup!!, message)
            }

            else -> TODO()
        }
    }
}