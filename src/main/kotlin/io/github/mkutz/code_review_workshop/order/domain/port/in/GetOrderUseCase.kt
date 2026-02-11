package io.github.mkutz.code_review_workshop.order.domain.port.`in`

import io.github.mkutz.code_review_workshop.order.domain.model.Order
import java.util.UUID

interface GetOrderUseCase {
    fun getOrder(id: UUID): Order?
    fun getAllOrders(): List<Order>
}
