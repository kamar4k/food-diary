package org.kamae.fooddairy.bot.entity

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.kamae.fooddairy.enums.ChatState

@Entity
@Table(name = "T_CHAT")
data class Chat(
    @Id
    val id: Long,
    val state: String
)
