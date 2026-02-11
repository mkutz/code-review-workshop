package io.github.mkutz.code_review_workshop.order.domain.port.out

import io.github.mkutz.code_review_workshop.order.domain.model.Order

interface SaveOrderPort {
    fun saveOrder(order: Order): Order
}
