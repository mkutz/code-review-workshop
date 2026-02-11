package io.github.mkutz.code_review_workshop.customer

import io.github.mkutz.code_review_workshop.ContainersConfig
import java.util.UUID
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.ObjectMapper

@SpringBootTest
@Import(ContainersConfig::class)
@AutoConfigureMockMvc
class CustomerControllerTests(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
) {

    @Test
    fun `create and get customer`() {
        val customerJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Alice Smith",
                "email" to "alice@example.com",
                "address" to "123 Main St",
            )
        )

        val result = mockMvc.perform(
            post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Alice Smith"))
            .andReturn()

        val id = objectMapper.readTree(result.response.contentAsString)["id"].asString()

        mockMvc.perform(get("/customers/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Alice Smith"))
            .andExpect(jsonPath("$.email").value("alice@example.com"))
            .andExpect(jsonPath("$.address").value("123 Main St"))
    }

    @Test
    fun `list all customers`() {
        val customerJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Bob Jones",
                "email" to "bob@example.com",
            )
        )

        mockMvc.perform(
            post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson)
        )
            .andExpect(status().isCreated)

        mockMvc.perform(get("/customers"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$").isNotEmpty)
    }

    @Test
    fun `update customer`() {
        val createJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Original Name",
                "email" to "original@example.com",
            )
        )

        val createResult = mockMvc.perform(
            post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(createResult.response.contentAsString)["id"].asString()

        val updateJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Updated Name",
                "email" to "updated@example.com",
                "address" to "456 Oak Ave",
            )
        )

        mockMvc.perform(
            put("/customers/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Updated Name"))
            .andExpect(jsonPath("$.email").value("updated@example.com"))
    }

    @Test
    fun `delete customer`() {
        val customerJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "To Delete",
                "email" to "delete@example.com",
            )
        )

        val createResult = mockMvc.perform(
            post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(customerJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(createResult.response.contentAsString)["id"].asString()

        mockMvc.perform(delete("/customers/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("To Delete"))

        mockMvc.perform(get("/customers/$id"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `get non-existent customer returns 404`() {
        mockMvc.perform(get("/customers/${UUID(0L, 0L)}"))
            .andExpect(status().isNotFound)
    }
}
