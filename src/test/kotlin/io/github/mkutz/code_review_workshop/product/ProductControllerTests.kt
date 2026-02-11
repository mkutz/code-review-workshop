package io.github.mkutz.code_review_workshop.product

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
class ProductControllerTests(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
) {

    @Test
    fun `create and get product`() {
        val productJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Test Product",
                "description" to "A test product",
                "price" to 19.99,
                "category" to "Test",
            )
        )

        val result = mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Test Product"))
            .andReturn()

        val id = objectMapper.readTree(result.response.contentAsString)["id"].asString()

        mockMvc.perform(get("/products/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Test Product"))
            .andExpect(jsonPath("$.price").value(19.99))
    }

    @Test
    fun `list all products`() {
        val productJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Listed Product",
                "price" to 9.99,
                "category" to "Test",
            )
        )

        mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson)
        )
            .andExpect(status().isCreated)

        mockMvc.perform(get("/products"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$").isNotEmpty)
    }

    @Test
    fun `update product`() {
        val createJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Original Name",
                "price" to 10.00,
                "category" to "Test",
            )
        )

        val createResult = mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(createResult.response.contentAsString)["id"].asString()

        val updateJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Updated Name",
                "price" to 15.00,
                "category" to "Updated",
            )
        )

        mockMvc.perform(
            put("/products/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Updated Name"))
            .andExpect(jsonPath("$.price").value(15.00))
    }

    @Test
    fun `delete product`() {
        val productJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "To Delete",
                "price" to 5.00,
                "category" to "Test",
            )
        )

        val createResult = mockMvc.perform(
            post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(productJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(createResult.response.contentAsString)["id"].asString()

        mockMvc.perform(delete("/products/$id"))
            .andExpect(status().isNoContent)

        mockMvc.perform(get("/products/$id"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `get non-existent product returns 404`() {
        mockMvc.perform(get("/products/${UUID(0L, 0L)}"))
            .andExpect(status().isNotFound)
    }
}