package io.github.mkutz.code_review_workshop.review

import io.github.mkutz.code_review_workshop.ContainersConfig
import java.util.UUID
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.context.annotation.Import
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import tools.jackson.databind.ObjectMapper

@SpringBootTest
@Import(ContainersConfig::class)
@AutoConfigureMockMvc
class ReviewControllerTests(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
) {

    private fun createCategory(): String {
        val categoryJson = objectMapper.writeValueAsString(
            mapOf("name" to "Test Category"),
        )

        val result = mockMvc.perform(
            post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        return objectMapper.readTree(result.response.contentAsString)["id"].asString()
    }

    @Test
    fun `create and list reviews`() {
        val categoryId = createCategory()

        val productJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Reviewed Product",
                "price" to 49.99,
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

        val productId = objectMapper.readTree(productResult.response.contentAsString)["id"].asString()

        val reviewJson = objectMapper.writeValueAsString(
            mapOf(
                "author" to "Alice",
                "rating" to 5,
                "comment" to "Great product!",
            )
        )

        mockMvc.perform(
            post("/products/$productId/reviews")
                .contentType(MediaType.APPLICATION_JSON)
                .content(reviewJson)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.author").value("Alice"))
            .andExpect(jsonPath("$.rating").value(5))

        mockMvc.perform(get("/products/$productId/reviews"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$[0].author").value("Alice"))
            .andExpect(jsonPath("$[0].rating").value(5))
    }

    @Test
    fun `reviews for non-existent product returns 404`() {
        mockMvc.perform(get("/products/${UUID(0L, 0L)}/reviews"))
            .andExpect(status().isNotFound)
    }
}
