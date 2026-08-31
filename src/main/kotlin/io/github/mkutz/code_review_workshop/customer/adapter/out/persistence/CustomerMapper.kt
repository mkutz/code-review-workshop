package io.github.mkutz.code_review_workshop.customer.adapter.out.persistence

import io.github.mkutz.code_review_workshop.customer.domain.model.Customer
import org.springframework.stereotype.Component

@Component
class CustomerMapper {

    fun toDomain(entity: CustomerEntity): Customer {
        return Customer(
            id = entity.id,
            name = entity.name,
            email = entity.email,
            address = entity.address,
        )
    }

    fun toEntity(customer: Customer): CustomerEntity {
        return CustomerEntity(
            id = customer.id,
            name = customer.name,
            email = customer.email,
            address = customer.address,
        )
    }
}
