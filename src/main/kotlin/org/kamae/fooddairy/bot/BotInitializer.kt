package org.kamae.fooddairy.bot

import org.springframework.context.annotation.Configuration
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession

@Configuration
class BotInitializer(val purchaseBot: PurchaseBot) {
    @EventListener(value = [ContextRefreshedEvent::class])
    fun init() {
        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
        telegramBotsApi.registerBot(purchaseBot)
    }
}