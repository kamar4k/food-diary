package org.kamae.fooddairy.bot

import org.kamae.fooddairy.bot.config.PurchaseBotConfiguration
import org.kamae.fooddairy.bot.service.PurchaseBotService
import org.kamae.fooddairy.bot.service.ResponseService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class PurchaseBot(
    private val purchaseBotConfiguration: PurchaseBotConfiguration,
    private val responseService: ResponseService,
    private val purchaseBotService: PurchaseBotService
) : TelegramLongPollingBot(purchaseBotConfiguration.token) {
    override fun getBotUsername() = purchaseBotConfiguration.name

    override fun onUpdateReceived(update: Update?) {
        if (update?.message != null) {
            val message = update.message
            logger.debug("Message: ${message.text}")
            when (message.text) {
                "/start" -> purchaseBotService.sendHelloMessage(message.chatId, this)
                else -> responseService.handleUserResponse(update, this)

            }
        } else if (update?.callbackQuery?.data != null) {

                when (update.callbackQuery.data) {
                    "/add_food_text" -> purchaseBotService.sendInputFoodMessage(update.callbackQuery.message.chatId, this)
                }
        }
    }


    companion object {
        private val logger = LoggerFactory.getLogger(PurchaseBot::class.java)
    }
}