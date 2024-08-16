package org.kamae.fooddairy

import org.kamae.fooddairy.bot.config.PurchaseBotConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@EnableConfigurationProperties(PurchaseBotConfiguration::class)
@ComponentScan(basePackages = arrayOf("org.kamae"))
class FoodDairyApplication

fun main(args: Array<String>) {
    runApplication<FoodDairyApplication>(*args)
}
