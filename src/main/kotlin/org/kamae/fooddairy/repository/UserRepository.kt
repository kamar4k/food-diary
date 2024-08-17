package org.kamae.fooddairy.repository

import org.kamae.fooddairy.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, String> {
    fun findUserById(id: String): User?
}