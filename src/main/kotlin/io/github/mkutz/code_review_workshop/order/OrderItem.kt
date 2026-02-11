package io.github.mkutz.code_review_workshop.order

import io.github.mkutz.code_review_workshop.product.Product
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import java.math.BigDecimal
import java.util.UUID
import com.fasterxml.jackson.annotation.JsonIgnore

@Entity
class OrderItem(
    @Id @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,
    @ManyToOne
    var product: Product,
    var quantity: Int,
    var unitPrice: BigDecimal,
    @ManyToOne
    @JsonIgnore
    var order: Order? = null,
)
