package org.kamae.fooddairy.service

import jakarta.transaction.Transactional
import org.kamae.fooddairy.entity.Group
import org.kamae.fooddairy.entity.Position
import org.kamae.fooddairy.entity.User
import org.springframework.stereotype.Service

interface PurchaseAdapter {
    fun addToPurchase(user: User, group: Group, userInput: String)
}

@Service
class PurchaseAdapterImpl(
    private val purchaseService: PurchaseService
) : PurchaseAdapter {
    @Transactional
    override fun addToPurchase(user: User, group: Group, userInput: String) {

        val purchase = purchaseService.getTodayPurchaseOrCreate(user, group)
        val strs = userInput.split("\n")

        strs.forEachIndexed {idx, it ->
            val product = Regex("(^.*?(?=\\s\\d))|(^\\D*\$)").find(it)?.value
            val weight = Regex("(?<=\\s)\\d*(?=г[\\s]?)").find(it)?.value
            val num = Regex("(?<=\\s)\\d+((?=\\s?)|(?=\\s?шт))").find(it)?.value
            purchaseService.addPosition(user, product!!, weight?.toDouble()?: -1.0, num?.toInt()?: 1, idx, purchase)
        }
    }

}