package org.kamae.fooddairy.bot.service

import org.kamae.fooddairy.bot.fw.MessageUtils
import org.kamae.fooddairy.bot.entity.Chat
import org.kamae.fooddairy.bot.enums.Command
import org.kamae.fooddairy.entity.User
import org.kamae.fooddairy.enums.ChatState
import org.kamae.fooddairy.bot.repository.ChatRepository
import org.kamae.fooddairy.repository.UserRepository
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot

interface ChatService {
    fun registration(id: String, name: String, chatId: Long, bot: TelegramLongPollingBot)

    fun initChat(id: Long)

    fun getChat(id: Long): Chat?

    fun getUser(id: String): User?

    fun saveUser(user: User): User

    fun setState(chatId: Long, chatState: ChatState)

    fun createGroup(chatId: Long, bot: TelegramLongPollingBot)
}

@Service
class ChatServiceImpl(
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository
) : ChatService {
    override fun registration(id: String, name: String, chatId: Long, bot: TelegramLongPollingBot) {
        val user = User(id, name, chatId, null)
        userRepository.save(user)
        var message = "Привет, $name, ты успешно зарегистрирован в системе!"
        MessageUtils.sendSimpleMessage(chatId, message, bot)

        chatRepository.updateChatState(chatId, ChatState.WAIT_COMMAND.name)
        //chatRepository.updateChatState(chatId, ChatState.WAIT_COMMAND.name)

        //val commands = listOf(Command.ADD_FOOD_TEXT, Command.ADD_FOOD_SELECT)
        //message = "Выбери действие:"
        //MessageUtils.sendButtonsMessage(chatId, message, commands, bot)
    }


    override fun initChat(id: Long) {
        chatRepository.save(Chat(id, ChatState.WAIT_NAME.name))
    }

    override fun getChat(id: Long): Chat? = chatRepository.getChatById(id)

    override fun getUser(id: String): User? = userRepository.findUserById(id)
    override fun saveUser(user: User): User = userRepository.save(user)


    override fun setState(chatId: Long, chatState: ChatState) {
        chatRepository.updateChatState(chatId, chatState.name)
    }

    override fun createGroup(chatId: Long, bot: TelegramLongPollingBot) {
        val message = "У тебя нет ни одной группы. Введи название своей новой группы"
        chatRepository.updateChatState(chatId, ChatState.WAIT_GROUP_NAME.name)
        MessageUtils.sendSimpleMessage(chatId, message, bot)
    }

}