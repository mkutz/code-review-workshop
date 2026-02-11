package io.github.mkutz.code_review_workshop.order

import io.github.mkutz.code_review_workshop.customer.CustomerRepository
import io.github.mkutz.code_review_workshop.product.ProductRepository
import java.math.BigDecimal
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class OrderItemRequest(
    val productId: UUID,
    val quantity: Int,
)

data class CreateOrderRequest(
    val customerId: UUID,
    val items: List<OrderItemRequest>,
)

data class UpdateStatusRequest(
    val status: String,
)

@RestController
@RequestMapping("/orders")
class OrderController(
    private val orderRepository: OrderRepository,
    private val customerRepository: CustomerRepository,
    private val productRepository: ProductRepository,
) {

    @PostMapping
    fun create(@RequestBody request: CreateOrderRequest): ResponseEntity<Order> {
        val customer = customerRepository.findById(request.customerId)
            .orElse(null) ?: return ResponseEntity.notFound().build()

        val order = Order(customer = customer)

        val items = request.items.map { itemRequest ->
            val product = productRepository.findById(itemRequest.productId)
                .orElse(null) ?: return ResponseEntity.notFound().build()

            OrderItem(
                product = product,
                quantity = itemRequest.quantity,
                unitPrice = product.price,
                order = order,
            )
        }

        order.items = items.toMutableList()
        order.totalPrice = items.fold(BigDecimal.ZERO) { total, item ->
            total + item.unitPrice * BigDecimal(item.quantity)
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(order))
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<Order> =
        orderRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @GetMapping
    fun findAll(): List<Order> = orderRepository.findAll()

    @PutMapping("/{id}/status")
    fun updateStatus(
        @PathVariable id: UUID,
        @RequestBody request: UpdateStatusRequest,
    ): ResponseEntity<Order> =
        orderRepository.findById(id)
            .map { order ->
                order.status = request.status
                ResponseEntity.ok(orderRepository.save(order))
            }
            .orElse(ResponseEntity.notFound().build())
}
