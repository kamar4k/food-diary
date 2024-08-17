package org.kamae.fooddairy.bot.service

import org.kamae.fooddairy.bot.PurchaseBot
import org.kamae.fooddairy.bot.aspect.annotation.BotAuthentication
import org.kamae.fooddairy.bot.aspect.annotation.BotInstance
import org.kamae.fooddairy.bot.aspect.annotation.BotUpdate
import org.kamae.fooddairy.bot.fw.MessageUtils
import org.kamae.fooddairy.entity.User
import org.kamae.fooddairy.service.GroupService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

interface BotAdapter {
    fun processUpdate(update: Update?, bot: TelegramLongPollingBot, user: User? = null)

    fun sendSimpleMessage(text: String, bot: TelegramLongPollingBot, chatId: Long)

    fun createGroup(bot: TelegramLongPollingBot, chatId: Long)
}


@Component
class BotAdapterImpl(
    private val responseService: ResponseService,
    private val purchaseBotService: PurchaseBotService,
) : BotAdapter {
    @BotAuthentication
    override fun processUpdate(update: Update?, bot: TelegramLongPollingBot, user: User?) {
        if (update?.message != null) {
            val message = update.message
            logger.debug("Message: ${message.text}")
            when (message.text) {
                "/start" -> purchaseBotService.sendHelloMessage(message.chatId, bot)
                else -> responseService.handleUserResponse(update, bot)

            }
        } else if (update?.callbackQuery?.data != null) {

            when (update.callbackQuery.data) {
                "/add_food_text" -> purchaseBotService.sendInputFoodMessage(update.callbackQuery.message.chatId, bot)
            }
        }
    }

    override fun sendSimpleMessage(text: String, bot: TelegramLongPollingBot, chatId: Long) {
        MessageUtils.sendSimpleMessage(chatId, text, bot)
    }

    override fun createGroup(bot: TelegramLongPollingBot, chatId: Long) {
        purchaseBotService.sendCreateGroupMessage(chatId, bot)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(BotAdapterImpl::class.java)
    }
}