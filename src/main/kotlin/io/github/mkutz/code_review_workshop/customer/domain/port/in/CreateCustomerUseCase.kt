package io.github.mkutz.code_review_workshop.customer.domain.port.`in`

import io.github.mkutz.code_review_workshop.customer.domain.model.Customer

interface CreateCustomerUseCase {

    fun createCustomer(customer: Customer): Customer
}
