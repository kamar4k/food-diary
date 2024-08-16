package org.kamae.fooddairy.entity

import jakarta.persistence.*

@Entity(name = "T_GROUP")
data class Group(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long?,
    @ManyToMany(targetEntity = User::class)
    val user: Set<User>?,
    val name: String?,
    @ManyToOne
    val owner: User?
)