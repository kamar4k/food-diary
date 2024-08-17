package org.kamae.fooddairy.bot.aspect

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.kamae.fooddairy.bot.exception.GroupsNotFoundException
import org.kamae.fooddairy.bot.exception.UserNotFoundException
import org.kamae.fooddairy.bot.service.ChatService
import org.kamae.fooddairy.entity.User
import org.kamae.fooddairy.enums.ChatState
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Aspect
@Component
class AuthenticationAspect(
    private val chatService: ChatService
) {

    //    @Around("@annotation(org.kamae.fooddairy.bot.aspect.annotation.BotAuthentication)")
    @Around("execution(* org.kamae.fooddairy.bot.service.BotAdapterImpl.processUpdate(..)) && args(update, bot, user))")
    fun processAuthentication(pjp: ProceedingJoinPoint, update: Update?, bot: TelegramLongPollingBot, user: User?) {
        var userName = ""
        var chatId = 0L
        if (update?.hasMessage() == true) {
            userName = update.message.chat.userName
            chatId = update.message.chatId
        } else if (update?.hasCallbackQuery() == true) {
            userName = update.callbackQuery.from.userName
            chatId = update.callbackQuery.from.id
        }
        var newUser: User? = null

        if (chatService.getChat(chatId)?.state != ChatState.WAIT_NAME.name &&
            update?.message?.text != "/start"
        ) {
            newUser = chatService.getUser(userName) ?: throw UserNotFoundException(userName)
        }
        if (newUser?.usedGroup == null) {
            if (newUser?.groups?.isNotEmpty() == true) {
                newUser.usedGroup = newUser!!.groups!!.first()
                chatService.saveUser(newUser)
            } else {
                throw GroupsNotFoundException(newUser?.id ?: "")
            }
        }
//TODO разнести по разным методам
        pjp.proceed(arrayOf(update, bot, newUser))
    }

    companion object {
        val logger: Logger = LoggerFactory.getLogger(AuthenticationAspect::class.java)
    }
}