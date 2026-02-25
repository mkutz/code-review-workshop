package io.github.mkutz.code_review_workshop.customer.domain.service

import io.github.mkutz.code_review_workshop.customer.domain.model.Customer
import io.github.mkutz.code_review_workshop.customer.domain.port.`in`.CreateCustomerUseCase
import io.github.mkutz.code_review_workshop.customer.domain.port.`in`.DeleteCustomerUseCase
import io.github.mkutz.code_review_workshop.customer.domain.port.`in`.GetCustomerUseCase
import io.github.mkutz.code_review_workshop.customer.domain.port.`in`.UpdateCustomerUseCase
import io.github.mkutz.code_review_workshop.customer.domain.port.out.LoadCustomerPort
import io.github.mkutz.code_review_workshop.customer.domain.port.out.SaveCustomerPort
import java.util.UUID
import org.springframework.stereotype.Service

@Service
class CustomerService(
    private val loadCustomerPort: LoadCustomerPort,
    private val saveCustomerPort: SaveCustomerPort,
) : GetCustomerUseCase, CreateCustomerUseCase, UpdateCustomerUseCase, DeleteCustomerUseCase {

    override fun getCustomer(id: UUID): Customer? {
        return loadCustomerPort.loadCustomer(id)
    }

    override fun getAllCustomers(): List<Customer> {
        return loadCustomerPort.loadAllCustomers()
    }

    override fun createCustomer(customer: Customer): Customer {
        return saveCustomerPort.saveCustomer(customer)
    }

    override fun updateCustomer(id: UUID, customer: Customer): Customer? {
        if (!loadCustomerPort.existsById(id)) return null
        return saveCustomerPort.saveCustomer(
            Customer(
                id = id,
                name = customer.name,
                email = customer.email,
                address = customer.address,
            )
        )
    }

    override fun deleteCustomer(id: UUID): Customer? {
        val customer = loadCustomerPort.loadCustomer(id) ?: return null
        saveCustomerPort.deleteCustomer(id)
        return customer
    }
}
