package org.kamae.fooddairy.bot.repository

import jakarta.transaction.Transactional
import org.kamae.fooddairy.bot.entity.Chat
import org.kamae.fooddairy.enums.ChatState
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface ChatRepository: JpaRepository<Chat, Long> {
    fun getChatById(id: Long): Chat?

    @Transactional
    @Modifying
    @Query("update Chat c set c.state = :state where c.id = :id")
    fun updateChatState(@Param("id") chatId: Long, @Param("state")state: String)
}