package io.github.mkutz.code_review_workshop.order.adapter.out.persistence

import io.github.mkutz.code_review_workshop.customer.adapter.out.persistence.CustomerEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "orders")
class OrderEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    @ManyToOne
    var customer: CustomerEntity? = null,
    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var items: MutableList<OrderItemEntity> = mutableListOf(),
    var status: String = "PENDING",
    var totalPrice: BigDecimal = BigDecimal.ZERO,
)
