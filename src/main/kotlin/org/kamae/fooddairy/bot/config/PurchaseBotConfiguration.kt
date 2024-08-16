package org.kamae.fooddairy.bot.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "bot")
data class PurchaseBotConfiguration(val token: String, val name: String)