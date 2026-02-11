package io.github.mkutz.code_review_workshop.order.domain.port.`in`

import io.github.mkutz.code_review_workshop.order.domain.model.Order

interface CreateOrderUseCase {
    fun createOrder(customerId: java.util.UUID, items: List<OrderItemCommand>): Order?
}

data class OrderItemCommand(val productId: java.util.UUID, val quantity: Int)
