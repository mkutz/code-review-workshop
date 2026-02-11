package io.github.mkutz.code_review_workshop.customer.domain.port.`in`

import io.github.mkutz.code_review_workshop.customer.domain.model.Customer
import java.util.UUID

interface GetCustomerUseCase {

    fun getCustomer(id: UUID): Customer?

    fun getAllCustomers(): List<Customer>
}
