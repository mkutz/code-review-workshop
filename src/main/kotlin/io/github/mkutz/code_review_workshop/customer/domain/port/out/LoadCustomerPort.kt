package io.github.mkutz.code_review_workshop.customer.domain.port.out

import io.github.mkutz.code_review_workshop.customer.domain.model.Customer
import java.util.UUID

interface LoadCustomerPort {

    fun loadCustomer(id: UUID): Customer?

    fun loadAllCustomers(): List<Customer>

    fun existsById(id: UUID): Boolean
}
