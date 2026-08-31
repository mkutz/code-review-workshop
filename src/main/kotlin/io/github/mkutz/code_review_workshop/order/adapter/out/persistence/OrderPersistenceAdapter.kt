package io.github.mkutz.code_review_workshop.order.adapter.out.persistence

import io.github.mkutz.code_review_workshop.customer.adapter.out.persistence.CustomerJpaRepository
import io.github.mkutz.code_review_workshop.order.domain.model.Order
import io.github.mkutz.code_review_workshop.order.domain.port.out.LoadOrderPort
import io.github.mkutz.code_review_workshop.order.domain.port.out.SaveOrderPort
import io.github.mkutz.code_review_workshop.product.adapter.out.persistence.ProductJpaRepository
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class OrderPersistenceAdapter(
    private val orderJpaRepository: OrderJpaRepository,
    private val customerJpaRepository: CustomerJpaRepository,
    private val productJpaRepository: ProductJpaRepository,
    private val orderMapper: OrderMapper,
) : LoadOrderPort, SaveOrderPort {

    override fun loadOrder(id: UUID): Order? =
        orderJpaRepository.findById(id)
            .map { orderMapper.toDomain(it) }
            .orElse(null)

    override fun loadAllOrders(): List<Order> =
        orderJpaRepository.findAll().map { orderMapper.toDomain(it) }

    override fun saveOrder(order: Order): Order {
        val customer = customerJpaRepository.findById(order.customerId).orElse(null)

        val orderEntity = OrderEntity(
            id = order.id,
            customer = customer,
            status = order.status,
            totalPrice = order.totalPrice,
        )

        val itemEntities = order.items.map { item ->
            val product = productJpaRepository.findById(item.productId).orElse(null)
            OrderItemEntity(
                id = item.id,
                product = product,
                quantity = item.quantity,
                unitPrice = item.unitPrice,
                order = orderEntity,
            )
        }

        orderEntity.items = itemEntities.toMutableList()
        return orderMapper.toDomain(orderJpaRepository.save(orderEntity))
    }
}
