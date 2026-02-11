package io.github.mkutz.code_review_workshop.customer.adapter.`in`.web

import io.github.mkutz.code_review_workshop.customer.domain.model.Customer
import io.github.mkutz.code_review_workshop.customer.domain.port.`in`.CreateCustomerUseCase
import io.github.mkutz.code_review_workshop.customer.domain.port.`in`.DeleteCustomerUseCase
import io.github.mkutz.code_review_workshop.customer.domain.port.`in`.GetCustomerUseCase
import io.github.mkutz.code_review_workshop.customer.domain.port.`in`.UpdateCustomerUseCase
import java.util.UUID
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(
    private val getCustomerUseCase: GetCustomerUseCase,
    private val createCustomerUseCase: CreateCustomerUseCase,
    private val updateCustomerUseCase: UpdateCustomerUseCase,
    private val deleteCustomerUseCase: DeleteCustomerUseCase,
) {

    @GetMapping
    fun findAll(): List<CustomerResponse> {
        return getCustomerUseCase.getAllCustomers().map { CustomerResponse.from(it) }
    }

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<CustomerResponse> {
        val customer = getCustomerUseCase.getCustomer(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(CustomerResponse.from(customer))
    }

    @PostMapping
    fun create(@RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
        val customer = Customer(name = request.name, email = request.email, address = request.address)
        val created = createCustomerUseCase.createCustomer(customer)
        return ResponseEntity.status(HttpStatus.CREATED).body(CustomerResponse.from(created))
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody request: CustomerRequest): ResponseEntity<CustomerResponse> {
        val customer = Customer(name = request.name, email = request.email, address = request.address)
        val updated = updateCustomerUseCase.updateCustomer(id, customer) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(CustomerResponse.from(updated))
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<CustomerResponse> {
        val deleted = deleteCustomerUseCase.deleteCustomer(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(CustomerResponse.from(deleted))
    }
}
