package io.github.mkutz.code_review_workshop.order.domain.port.out

import io.github.mkutz.code_review_workshop.order.domain.model.Order
import java.util.UUID

interface LoadOrderPort {
    fun loadOrder(id: UUID): Order?
    fun loadAllOrders(): List<Order>
}
