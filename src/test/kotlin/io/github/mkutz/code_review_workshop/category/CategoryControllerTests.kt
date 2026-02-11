package io.github.mkutz.code_review_workshop.category

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
class CategoryControllerTests(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper,
) {

    @Test
    fun `create and get category`() {
        val categoryJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Electronics",
                "description" to "Electronic devices and accessories",
            )
        )

        val result = mockMvc.perform(
            post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson)
        )
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.name").value("Electronics"))
            .andReturn()

        val id = objectMapper.readTree(result.response.contentAsString)["id"].asString()

        mockMvc.perform(get("/categories/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Electronics"))
            .andExpect(jsonPath("$.description").value("Electronic devices and accessories"))
    }

    @Test
    fun `list all categories`() {
        val categoryJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Books",
            )
        )

        mockMvc.perform(
            post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson)
        )
            .andExpect(status().isCreated)

        mockMvc.perform(get("/categories"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$").isArray)
            .andExpect(jsonPath("$").isNotEmpty)
    }

    @Test
    fun `update category`() {
        val createJson = objectMapper.writeValueAsString(
            mapOf("name" to "Original Category")
        )

        val createResult = mockMvc.perform(
            post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(createResult.response.contentAsString)["id"].asString()

        val updateJson = objectMapper.writeValueAsString(
            mapOf(
                "name" to "Updated Category",
                "description" to "Now with a description",
            )
        )

        mockMvc.perform(
            put("/categories/$id")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updateJson)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("Updated Category"))
    }

    @Test
    fun `delete category`() {
        val categoryJson = objectMapper.writeValueAsString(
            mapOf("name" to "To Delete")
        )

        val createResult = mockMvc.perform(
            post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson)
        )
            .andExpect(status().isCreated)
            .andReturn()

        val id = objectMapper.readTree(createResult.response.contentAsString)["id"].asString()

        mockMvc.perform(delete("/categories/$id"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value("To Delete"))

        mockMvc.perform(get("/categories/$id"))
            .andExpect(status().isNotFound)
    }

    @Test
    fun `get non-existent category returns 404`() {
        mockMvc.perform(get("/categories/${UUID(0L, 0L)}"))
            .andExpect(status().isNotFound)
    }
}
