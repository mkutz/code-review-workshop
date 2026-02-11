package io.github.mkutz.code_review_workshop.order.adapter.`in`.web

import io.github.mkutz.code_review_workshop.order.domain.model.Order
import io.github.mkutz.code_review_workshop.order.domain.model.OrderItem
import java.math.BigDecimal
import java.util.UUID

data class OrderResponse(
    val id: UUID?,
    val customerId: UUID,
    val items: List<OrderItemResponse>,
    val status: String,
    val totalPrice: BigDecimal
) {
    companion object {
        fun from(order: Order) = OrderResponse(
            id = order.id,
            customerId = order.customerId,
            items = order.items.map { OrderItemResponse.from(it) },
            status = order.status,
            totalPrice = order.totalPrice
        )
    }
}

data class OrderItemResponse(
    val id: UUID?,
    val productId: UUID,
    val quantity: Int,
    val unitPrice: BigDecimal
) {
    companion object {
        fun from(item: OrderItem) = OrderItemResponse(
            id = item.id,
            productId = item.productId,
            quantity = item.quantity,
            unitPrice = item.unitPrice
        )
    }
}
