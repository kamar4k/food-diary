package org.kamae.fooddairy.repository

import org.kamae.fooddairy.entity.Group
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GroupRepository: JpaRepository<Group, Long> {
}