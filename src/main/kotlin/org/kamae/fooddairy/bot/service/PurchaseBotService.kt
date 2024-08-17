package org.kamae.fooddairy.bot.service

import org.kamae.fooddairy.bot.aspect.annotation.BotAuthentication
import org.kamae.fooddairy.bot.fw.MessageUtils
import org.kamae.fooddairy.enums.ChatState
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot

interface PurchaseBotService {
    fun sendHelloMessage(chatId: Long, bot: TelegramLongPollingBot)

    fun sendInputFoodMessage(chatId: Long, bot: TelegramLongPollingBot)

    fun sendGroupCreatedMessage(chatId: Long, bot: TelegramLongPollingBot)

    fun sendCreateGroupMessage(chatId: Long, bot: TelegramLongPollingBot)
}

@Service
class PurchaseBotServiceImpl(
    private val chatService: ChatService
): PurchaseBotService {
    override fun sendHelloMessage(chatId: Long, bot: TelegramLongPollingBot) {
        val message = "Привет! Бот предназначен для ведения дневника питания. Для начала работы со мной напиши своё имя"
        MessageUtils.sendSimpleMessage(chatId, message, bot)
        chatService.initChat(chatId)
    }

    override fun sendInputFoodMessage(chatId: Long, bot: TelegramLongPollingBot) {
        chatService.setState(chatId, ChatState.WAIT_FOOD_INPUT)
        val message = "Введите в одном из следующих форматов:\n" +
                "1. Название блюда, вес г, дата, время\n" +
                "2. Название блюда, вес г\n" +
                "3. Название блюда, дата, время\n" +
                "4. Название блюда, время\n"
        MessageUtils.sendSimpleMessage(chatId, message, bot)
    }

    override fun sendGroupCreatedMessage(chatId: Long, bot: TelegramLongPollingBot) {
        chatService.setState(chatId, ChatState.WAIT_POSITIONS)
        val message = "Группа успешно зарегистрирована"

        MessageUtils.sendSimpleMessage(chatId, message, bot)
    }

    override fun sendCreateGroupMessage(chatId: Long, bot: TelegramLongPollingBot) {
        chatService.createGroup(chatId, bot)
    }

}