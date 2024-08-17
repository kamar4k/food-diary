package org.kamae.fooddairy.bot.exception

class GroupsNotFoundException(userId: String): RuntimeException("Missing groups for user=$userId") {
}