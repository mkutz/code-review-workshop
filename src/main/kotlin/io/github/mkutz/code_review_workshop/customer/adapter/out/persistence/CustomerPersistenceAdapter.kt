package io.github.mkutz.code_review_workshop.customer.adapter.out.persistence

import io.github.mkutz.code_review_workshop.customer.domain.model.Customer
import io.github.mkutz.code_review_workshop.customer.domain.port.out.LoadCustomerPort
import io.github.mkutz.code_review_workshop.customer.domain.port.out.SaveCustomerPort
import java.util.UUID
import org.springframework.stereotype.Component

@Component
class CustomerPersistenceAdapter(
    private val customerJpaRepository: CustomerJpaRepository,
    private val customerMapper: CustomerMapper,
) : LoadCustomerPort, SaveCustomerPort {

    override fun loadCustomer(id: UUID): Customer? {
        return customerJpaRepository.findById(id)
            .map { customerMapper.toDomain(it) }
            .orElse(null)
    }

    override fun loadAllCustomers(): List<Customer> {
        return customerJpaRepository.findAll().map { customerMapper.toDomain(it) }
    }

    override fun existsById(id: UUID): Boolean {
        return customerJpaRepository.existsById(id)
    }

    override fun saveCustomer(customer: Customer): Customer {
        val entity = customerMapper.toEntity(customer)
        return customerMapper.toDomain(customerJpaRepository.save(entity))
    }

    override fun deleteCustomer(id: UUID) {
        customerJpaRepository.deleteById(id)
    }
}
