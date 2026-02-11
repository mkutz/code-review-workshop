package io.github.mkutz.code_review_workshop.order.domain.port.`in`

import io.github.mkutz.code_review_workshop.order.domain.model.Order
import java.util.UUID

interface UpdateOrderStatusUseCase {
    fun updateOrderStatus(id: UUID, status: String): Order?
}
