package org.kamae.fooddairy.entity

import jakarta.persistence.*

@Entity
@Table(name = "T_USER")
open class User(
        @Id
        val id: String,
        val name: String,
        val chatId: Long,
        @ManyToMany(targetEntity = Group::class)
        val group: Set<Group>?
) {
        @OneToOne
        var usedGroup: Group? = null
}
