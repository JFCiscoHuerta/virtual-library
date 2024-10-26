package com.gklyphon.VirtualLibrary.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gklyphon.VirtualLibrary.Data;
import com.gklyphon.VirtualLibrary.service.impl.AuthorServiceImpl;
import com.gklyphon.VirtualLibrary.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for validating the security configuration of the Virtual Library API.
 * This class ensures that endpoints are accessed according to the defined roles and permissions.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 19-Oct-2024
 */
@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTest {

    @MockBean
    BookServiceImpl bookService;

    @MockBean
    AuthorServiceImpl authorService;

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper objectMapper;
    final String API_URL = "/v1/books";

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Verifies that a non-admin user is denied access to the save-book endpoint.
     */
    @Test
    @Disabled
    void shouldDenyAccessToSaveBooksForNonAdminUser() throws Exception {

        mockMvc.perform(
                MockMvcRequestBuilders.post(API_URL + "/save-book")
                        .content(objectMapper.writeValueAsString(Data.BOOK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isForbidden());
    }

    /**
     * Verifies that an admin user can save a book successfully.
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldAllowAccessToSaveBooksForAdminUser() throws Exception {
        when(authorService.findById(anyLong())).thenReturn(Data.AUTHOR);
        mockMvc.perform(
                MockMvcRequestBuilders.post(API_URL + "/save-book")
                        .param("author_id", "1")
                        .content(objectMapper.writeValueAsString(Data.BOOK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isCreated());
    }


    /**
     * Verifies that a non-admin user is denied access to the delete-book endpoint.
     */
    @Test
    @Disabled
    void shouldDenyAccessToDeleteBooksForNonAdminUser() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.delete(API_URL + "/delete-book/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isForbidden());
    }

    /**
     * Verifies that an admin user can delete a book successfully.
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldAllowAccessToDeleteBookForAdminUser() throws Exception {

        mockMvc.perform(
                        MockMvcRequestBuilders.delete(API_URL + "/delete-book/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .with(csrf())
                )
                .andExpect(status().isOk());
    }

    /**
     * Verifies that any user can access the findById endpoint.
     */
    @Test
    void shouldAllowAccessToFindById() throws Exception {
        when(bookService.findById(anyLong())).thenReturn(Data.BOOK);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    /**
     * Verifies that an authenticated user can access the findById endpoint.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldAllowAccessToFindByIdWhenAuthenticated() throws Exception {
        when(bookService.findById(anyLong())).thenReturn(Data.BOOK);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    /**
     * Verifies that any user can access the findByTitle endpoint.
     */
    @Test
    void shouldAllowAccessToFindByTitle() throws Exception {
        when(bookService.findByTitle(anyString())).thenReturn(Data.BOOK);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/by-title")
                                .param("title", "Book1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    /**
     * Verifies that an authenticated user can access the findByTitle endpoint.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldAllowAccessToFindByTitleWhenAuthenticated() throws Exception {
        when(bookService.findByTitle(anyString())).thenReturn(Data.BOOK);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/by-title")
                                .param("title", "Book1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    /**
     * Verifies that any user can access the findByIsbn endpoint.
     */
    @Test
    void shouldAllowAccessToFindByIsbn() throws Exception {
        when(bookService.findByIsbn(anyString())).thenReturn(Data.BOOK);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/by-isbn")
                                .param("isbn", "ISBN1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }

    /**
     * Verifies that an authenticated user can access the findByIsbn endpoint.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldAllowAccessToFindByIsbnWhenAuthenticated() throws Exception {
        when(bookService.findByIsbn(anyString())).thenReturn(Data.BOOK);
        mockMvc.perform(
                        MockMvcRequestBuilders.get(API_URL + "/by-isbn")
                                .param("isbn", "ISBN1")
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk());
    }


    /**
     * Verifies that requests to non-existent resources return a 204 No Content status.
     *
     * @param endpoint the endpoint to test.
     */
    @ParameterizedTest
    @ValueSource(strings = { "/by-title?title=Book1", "/by-isbn?isbn=ISBN1", "/1001"})
    void shouldReturnNotFoundWhenBookDoesNoExist(String endpoint) throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL + endpoint)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNoContent());
    }
}
