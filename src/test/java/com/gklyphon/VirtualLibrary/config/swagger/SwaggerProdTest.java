package com.gklyphon.VirtualLibrary.config.swagger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for verifying Swagger UI access in the production profile.
 * This class contains tests that ensure the Swagger UI is correctly configured
 * and accessible based on the active Spring profile.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 20-Oct-2024
 */
@SpringBootTest
@ActiveProfiles("prod")
@AutoConfigureMockMvc
public class SwaggerProdTest {

    @Autowired
    MockMvc mockMvc;

    /**
     * Test to verify that the Swagger UI endpoint is not found in the production profile.
     * It performs a GET request to the Swagger UI path and expects a 404 Not Found status.
     *
     * @throws Exception if the request fails or the response is not as expected.
     */
    @Test
    void testSwaggerOkInDevProfile() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.get("/swagger-ui/index.html")
        )
                .andExpect(status().isNotFound());

    }
}
