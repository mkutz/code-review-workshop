package io.github.mkutz.code_review_workshop.order.adapter.`in`.web

import java.util.UUID

data class CreateOrderRequest(
    val customerId: UUID,
    val items: List<OrderItemRequest>,
)

data class OrderItemRequest(
    val productId: UUID,
    val quantity: Int,
)

data class UpdateStatusRequest(
    val status: String,
)
