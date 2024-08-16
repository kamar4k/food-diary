package org.kamae.fooddairy.enums

enum class ChatState {
    WAIT_NAME,
    WAIT_GROUP_NAME,
    WAIT_COMMAND,
    WAIT_POSITIONS,
    WAIT_FOOD_INPUT
    ;

    override fun toString() = name
}