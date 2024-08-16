package org.kamae.fooddairy.repository

import org.kamae.fooddairy.entity.Group
import org.kamae.fooddairy.entity.Purchase
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDate

interface PurchaseRepository : JpaRepository<Purchase, Long> {

    fun findPurchaseByTargetDateAndGroup(date: LocalDate, group: Group): Purchase?
}