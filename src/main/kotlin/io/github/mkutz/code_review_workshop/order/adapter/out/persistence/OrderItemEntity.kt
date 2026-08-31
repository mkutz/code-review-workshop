package io.github.mkutz.code_review_workshop.order.adapter.out.persistence

import io.github.mkutz.code_review_workshop.product.adapter.out.persistence.ProductEntity
import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.util.UUID

@Entity
class OrderItemEntity(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    @ManyToOne
    var product: ProductEntity? = null,
    var quantity: Int = 0,
    var unitPrice: BigDecimal = BigDecimal.ZERO,
    @ManyToOne
    @JsonIgnore
    var order: OrderEntity? = null,
)
