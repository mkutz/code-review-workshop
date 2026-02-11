package io.github.mkutz.code_review_workshop.order.domain.service

import io.github.mkutz.code_review_workshop.order.domain.model.Order
import io.github.mkutz.code_review_workshop.order.domain.model.OrderItem
import io.github.mkutz.code_review_workshop.order.domain.port.`in`.CreateOrderUseCase
import io.github.mkutz.code_review_workshop.order.domain.port.`in`.GetOrderUseCase
import io.github.mkutz.code_review_workshop.order.domain.port.`in`.OrderItemCommand
import io.github.mkutz.code_review_workshop.order.domain.port.`in`.UpdateOrderStatusUseCase
import io.github.mkutz.code_review_workshop.order.domain.port.out.LoadOrderPort
import io.github.mkutz.code_review_workshop.order.domain.port.out.SaveOrderPort
import io.github.mkutz.code_review_workshop.product.domain.port.out.LoadProductPort
import io.github.mkutz.code_review_workshop.customer.domain.port.out.LoadCustomerPort
import java.math.BigDecimal
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val loadOrderPort: LoadOrderPort,
    private val saveOrderPort: SaveOrderPort,
    private val loadProductPort: LoadProductPort,
    private val loadCustomerPort: LoadCustomerPort,
) : CreateOrderUseCase, GetOrderUseCase, UpdateOrderStatusUseCase {

    override fun createOrder(customerId: UUID, items: List<OrderItemCommand>): Order? {
        if (loadCustomerPort.loadCustomer(customerId) == null) return null

        val orderItems = items.map { cmd ->
            val product = loadProductPort.loadProduct(cmd.productId) ?: return null
            OrderItem(
                productId = cmd.productId,
                quantity = cmd.quantity,
                unitPrice = product.price,
            )
        }

        val totalPrice = orderItems.fold(BigDecimal.ZERO) { total, item ->
            total + item.unitPrice * BigDecimal(item.quantity)
        }

        val order = Order(
            customerId = customerId,
            items = orderItems,
            totalPrice = totalPrice,
        )

        return saveOrderPort.saveOrder(order)
    }

    override fun getOrder(id: UUID): Order? = loadOrderPort.loadOrder(id)

    override fun getAllOrders(): List<Order> = loadOrderPort.loadAllOrders()

    override fun updateOrderStatus(id: UUID, status: String): Order? {
        val existing = loadOrderPort.loadOrder(id) ?: return null
        val updated = Order(
            id = existing.id,
            customerId = existing.customerId,
            items = existing.items,
            status = status,
            totalPrice = existing.totalPrice,
        )
        return saveOrderPort.saveOrder(updated)
    }
}
