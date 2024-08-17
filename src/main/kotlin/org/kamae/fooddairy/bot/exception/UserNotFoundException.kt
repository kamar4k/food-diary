package org.kamae.fooddairy.bot.exception

class UserNotFoundException(
    val userName: String
): RuntimeException("User $userName not found") {
}