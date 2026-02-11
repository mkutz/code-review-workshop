package io.github.mkutz.code_review_workshop.order

import io.github.mkutz.code_review_workshop.ContainersConfig
import java.util.UUID
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.ObjectMapper

@SpringBootTest
@Import(ContainersConfig::class)
@AutoConfigureMockMvc
class OrderControllerTests(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
) {

    private lateinit var customerId: String
    private lateinit var productId: String

    @BeforeEach
    fun setUp() {
        val categoryJson = objectMapper.writeValueAsString(
            mapOf("name" to "Test Category"),
        )

        val categoryResult = mockMvc.perform(
            post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        val categoryId = objectMapper.readTree(categoryResult.response.contentAsString)["id"].asString()

        val customerJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Test Customer",
                "email" to "test@example.com",
            )
        )

        val customerResult = mockMvc.perform(
            post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        customerId = objectMapper.readTree(customerResult.response.contentAsString)["id"].asString()

        val productJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Test Product",
                "price" to 25.00,
                "category" to mapOf("id" to categoryId),
            )
        )

        val productResult = mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        productId = objectMapper.readTree(productResult.response.contentAsString)["id"].asString()
    }

    @Test
    fun `create and get order`() {
        val orderJson = objectMapper.writeValueAsString(
            mapOf(
                "customerId" to customerId,
                "items" to listOf(
                    mapOf(
                        "productId" to productId,
                        "quantity" to 2,
                    )
                ),
            )
        )

        val result = mockMvc.perform(
            post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.status").value("PENDING"))
            .andExpect(jsonPath("$.totalPrice").value(50.00))
            .andExpect(jsonPath("$.items").isArray)
            .andExpect(jsonPath("$.items[0].quantity").value(2))
            .andReturn()

        val id = objectMapper.readTree(result.response.contentAsString)["id"].asString()

        mockMvc.perform(get("/orders/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("PENDING"))
            .andExpect(jsonPath("$.totalPrice").value(50.00))
    }

    @Test
    fun `list all orders`() {
        val orderJson = objectMapper.writeValueAsString(
            mapOf(
                "customerId" to customerId,
                "items" to listOf(
                    mapOf(
                        "productId" to productId,
                        "quantity" to 1,
                    )
                ),
            )
        )

        mockMvc.perform(
            post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)
        )
            .andExpect(status().isCreated)

        mockMvc.perform(get("/orders"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$").isNotEmpty)
    }

    @Test
    fun `update order status`() {
        val orderJson = objectMapper.writeValueAsString(
            mapOf(
                "customerId" to customerId,
                "items" to listOf(
                    mapOf(
                        "productId" to productId,
                        "quantity" to 1,
                    )
                ),
            )
        )

        val createResult = mockMvc.perform(
            post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(createResult.response.contentAsString)["id"].asString()

        val statusJson = objectMapper.writeValueAsString(
            mapOf("status" to "SHIPPED"),
        )

        mockMvc.perform(
            put("/orders/$id/status")
                .contentType(MediaType.APPLICATION_JSON)
                .content(statusJson)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.status").value("SHIPPED"))
    }

    @Test
    fun `create order with non-existent customer returns 404`() {
        val orderJson = objectMapper.writeValueAsString(
            mapOf(
                "customerId" to UUID(0L, 0L),
                "items" to listOf(
                    mapOf(
                        "productId" to productId,
                        "quantity" to 1,
                    )
                ),
            )
        )

        mockMvc.perform(
            post("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(orderJson)
        )
            .andExpect(status().isNotFound)
    }

    @Test
    fun `get non-existent order returns 404`() {
        mockMvc.perform(get("/orders/${UUID(0L, 0L)}"))
            .andExpect(status().isNotFound)
    }
}
