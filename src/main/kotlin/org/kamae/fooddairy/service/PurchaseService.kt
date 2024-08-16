package org.kamae.fooddairy.service

import org.kamae.fooddairy.entity.Group
import org.kamae.fooddairy.entity.Purchase
import org.kamae.fooddairy.entity.Position
import org.kamae.fooddairy.entity.User
import org.kamae.fooddairy.repository.PurchaseRepository
import org.kamae.fooddairy.repository.PositionRepository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalDateTime

interface PurchaseService {
    fun addPosition(user: User, name: String, weight: Double?, num: Int?, number: Int, purchase: Purchase): Position

    fun getTodayPurchaseOrCreate(user: User, group: Group): Purchase

    fun createOrUpdatePurchase(purchase: Purchase)
}

@Service
class PurchaseServiceImpl(
    private val purchaseRepository: PurchaseRepository,
    private val positionRepository: PositionRepository
) : PurchaseService {
    override fun addPosition(
        user: User,
        name: String,
        weight: Double?,
        num: Int?,
        number: Int,
        purchase: Purchase
    ): Position {
        val position = Position(null, name, num, weight, null, null, false, number, purchase)

        return positionRepository.save(position)
    }

    override fun getTodayPurchaseOrCreate(user: User, group: Group): Purchase {
        return purchaseRepository.findPurchaseByTargetDateAndGroup(LocalDate.now(), group) ?: let {
            val purchase = Purchase(null, group, user, LocalDateTime.now(), LocalDate.now())
            return purchaseRepository.save(purchase)
        }
    }

    override fun createOrUpdatePurchase(purchase: Purchase) {
        purchaseRepository.save(purchase)
    }


}