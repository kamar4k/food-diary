package org.kamae.fooddairy.bot.fw

import org.kamae.fooddairy.bot.enums.Command
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import org.telegram.telegrambots.meta.generics.LongPollingBot
import org.telegram.telegrambots.meta.generics.TelegramBot

object MessageUtils {
    fun sendSimpleMessage(chatId: Long, textToSend: String, bot: TelegramLongPollingBot) {
        val sendMessage = SendMessage()

        sendMessage.enableNotification()
        sendMessage.setChatId(chatId);
        sendMessage.text = textToSend

        bot.execute(sendMessage)
    }

    fun sendButtonsMessage(chatId: Long, message: String, commands: List<Command>, bot: TelegramLongPollingBot) {
        val sendMessage = SendMessage()

        val markup = InlineKeyboardMarkup()

        val row = commands.map {
            InlineKeyboardButton.builder().text(it.text).callbackData(it.command).build()
        }
        markup.keyboard = listOf(row)

        sendMessage.replyMarkup = markup
        sendMessage.text = message
        sendMessage.setChatId(chatId)

        bot.execute(sendMessage)
    }
}