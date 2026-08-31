package io.github.mkutz.code_review_workshop.customer.domain.port.out

import io.github.mkutz.code_review_workshop.customer.domain.model.Customer
import java.util.UUID

interface SaveCustomerPort {

    fun saveCustomer(customer: Customer): Customer

    fun deleteCustomer(id: UUID)
}
