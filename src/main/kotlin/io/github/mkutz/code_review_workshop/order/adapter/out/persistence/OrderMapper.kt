package io.github.mkutz.code_review_workshop.order.adapter.out.persistence

import io.github.mkutz.code_review_workshop.order.domain.model.Order
import io.github.mkutz.code_review_workshop.order.domain.model.OrderItem
import org.springframework.stereotype.Component

@Component
class OrderMapper {

    fun toDomain(entity: OrderEntity): Order =
        Order(
            id = entity.id,
            customerId = entity.customer!!.id!!,
            items = entity.items.map { toItemDomain(it) },
            status = entity.status,
            totalPrice = entity.totalPrice,
        )

    fun toItemDomain(entity: OrderItemEntity): OrderItem =
        OrderItem(
            id = entity.id,
            productId = entity.product!!.id!!,
            quantity = entity.quantity,
            unitPrice = entity.unitPrice,
        )
}
