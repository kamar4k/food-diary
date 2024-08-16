package org.kamae.fooddairy.entity

import jakarta.persistence.*
import java.time.LocalDate
import java.time.LocalDateTime

@Entity
@Table(name="T_INGESTION")
data class Purchase(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long?,
    @ManyToOne
    val group: Group?,
    @OneToOne
    val createBy: User?,
    val createDateTime: LocalDateTime?,
    val targetDate: LocalDate?
)