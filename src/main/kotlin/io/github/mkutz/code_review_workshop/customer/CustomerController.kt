package io.github.mkutz.code_review_workshop.customer

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
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/customers")
class CustomerController(private val customerRepository: CustomerRepository) {

    @GetMapping
    fun findAll(): List<Customer> = customerRepository.findAll()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: UUID): ResponseEntity<Customer> =
        customerRepository.findById(id)
            .map { ResponseEntity.ok(it) }
            .orElse(ResponseEntity.notFound().build())

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody customer: Customer): Customer = customerRepository.save(customer)

    @PutMapping("/{id}")
    fun update(@PathVariable id: UUID, @RequestBody customer: Customer): ResponseEntity<Customer> =
        if (customerRepository.existsById(id)) {
            customer.id = id
            ResponseEntity.ok(customerRepository.save(customer))
        } else {
            ResponseEntity.notFound().build()
        }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: UUID): ResponseEntity<Customer> =
        customerRepository.findById(id)
            .map { customer ->
                customerRepository.delete(customer)
                ResponseEntity.ok(customer)
            }
            .orElse(ResponseEntity.notFound().build())
}
