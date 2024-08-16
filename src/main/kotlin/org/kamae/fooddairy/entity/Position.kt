package org.kamae.fooddairy.entity

import jakarta.persistence.*

@Entity
@Table(name = "T_POSITION")
data class Position(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long?,
    val name: String?,
    val num: Int?,
    val weight: Double?,
    val cost: Double?,
    val costTotal: Double?,
    val released: Boolean,
    val number: Int,
    @ManyToOne
    val purchase: Purchase
)