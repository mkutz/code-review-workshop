package io.github.mkutz.code_review_workshop.order.adapter.`in`.web

import io.github.mkutz.code_review_workshop.order.domain.port.`in`.CreateOrderUseCase
import io.github.mkutz.code_review_workshop.order.domain.port.`in`.GetOrderUseCase
import io.github.mkutz.code_review_workshop.order.domain.port.`in`.OrderItemCommand
import io.github.mkutz.code_review_workshop.order.domain.port.`in`.UpdateOrderStatusUseCase
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/orders")
class OrderController(
    private val createOrderUseCase: CreateOrderUseCase,
    private val getOrderUseCase: GetOrderUseCase,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase,
) {

    @PostMapping
    fun create(@RequestBody request: CreateOrderRequest): ResponseEntity<OrderResponse> {
        val commands = request.items.map { OrderItemCommand(productId = it.productId, quantity = it.quantity) }
        val order = createOrderUseCase.createOrder(request.customerId, commands)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponse.from(order))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<OrderResponse> {
        val order = getOrderUseCase.getOrder(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(OrderResponse.from(order))
    }

    @GetMapping
    fun findAll(): List<OrderResponse> =
        getOrderUseCase.getAllOrders().map { OrderResponse.from(it) }

    @PutMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: UUID,
        @RequestBody request: UpdateStatusRequest,
    ): ResponseEntity<OrderResponse> {
        val order = updateOrderStatusUseCase.updateOrderStatus(id, request.status)
            ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(OrderResponse.from(order))
    }
}
