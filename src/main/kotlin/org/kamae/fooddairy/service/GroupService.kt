package org.kamae.fooddairy.service

import org.kamae.fooddairy.entity.Group
import org.kamae.fooddairy.entity.User
import org.kamae.fooddairy.repository.GroupRepository
import org.kamae.fooddairy.repository.UserRepository
import org.springframework.stereotype.Service

interface GroupService {
    fun createGroup(name: String, user: User)
}

@Service
class GroupServiceImpl(
    private val groupRepository: GroupRepository,
    private val userRepository: UserRepository
) : GroupService {
    override fun createGroup(name: String, user: User) {
        val group = groupRepository.save(Group(null, setOf(user), name, user))

        user.usedGroup = group
        userRepository.save(user)
    }

}