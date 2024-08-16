package org.kamae.fooddairy.repository

import org.kamae.fooddairy.entity.Position
import org.springframework.data.jpa.repository.JpaRepository

interface PositionRepository: JpaRepository<Position, Long> {
}