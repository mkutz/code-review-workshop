package io.github.mkutz.code_review_workshop.customer.adapter.`in`.web

data class CustomerRequest(
    val name: String,
    val email: String,
    val address: String? = null,
)
