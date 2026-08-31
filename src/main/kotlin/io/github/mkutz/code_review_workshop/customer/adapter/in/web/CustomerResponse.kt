package io.github.mkutz.code_review_workshop.customer.adapter.`in`.web

import io.github.mkutz.code_review_workshop.customer.domain.model.Customer
import java.util.UUID

data class CustomerResponse(
    val id: UUID?,
    val name: String,
    val email: String,
    val address: String?,
) {
    companion object {
        fun from(customer: Customer): CustomerResponse {
            return CustomerResponse(
                id = customer.id,
                name = customer.name,
                email = customer.email,
                address = customer.address,
            )
        }
    }
}
