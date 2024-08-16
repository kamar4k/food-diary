package org.kamae.fooddairy.bot.enums

enum class Command(
    val text: String,
    val command: String
) {
    ADD_FOOD_TEXT("Ввести еду", "/add_food_text"),
    ADD_FOOD_SELECT("Выбрать из списка", "/add_food_select");

}