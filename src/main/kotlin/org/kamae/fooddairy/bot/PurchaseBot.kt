package org.kamae.fooddairy.bot

import org.kamae.fooddairy.bot.aspect.annotation.BotAuthentication
import org.kamae.fooddairy.bot.config.PurchaseBotConfiguration
import org.kamae.fooddairy.bot.exception.GroupsNotFoundException
import org.kamae.fooddairy.bot.exception.UserNotFoundException
import org.kamae.fooddairy.bot.service.BotAdapter
import org.kamae.fooddairy.bot.service.PurchaseBotService
import org.kamae.fooddairy.bot.service.ResponseService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.objects.Update

@Component
class PurchaseBot(
    private val purchaseBotConfiguration: PurchaseBotConfiguration,
    private val botAdapter: BotAdapter
) : TelegramLongPollingBot(purchaseBotConfiguration.token) {
    override fun getBotUsername() = purchaseBotConfiguration.name

    override fun onUpdateReceived(update: Update?) {
        try {
            botAdapter.processUpdate(update, this)
        } catch (e: UserNotFoundException) {
            botAdapter.sendSimpleMessage(
                "Для работы с ботом надо зарегистрироваться! Для регистрации введите команду /start",
                this,
                update?.message?.chatId ?: update?.callbackQuery?.from?.id!!
            )
        } catch (e: GroupsNotFoundException) {
            botAdapter.createGroup(this, update?.message?.chatId ?: update?.callbackQuery?.from?.id!!)
        }
    }


    companion object {
        private val logger = LoggerFactory.getLogger(PurchaseBot::class.java)
    }
}