package com.gklyphon.VirtualLibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.gklyphon.VirtualLibrary.Data;
import com.gklyphon.VirtualLibrary.model.entity.Author;
import com.gklyphon.VirtualLibrary.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test class for {@link com.gklyphon.VirtualLibrary.controller.AuthorController}.
 * Verifies the behavior of the endpoints related to the Author entity.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 22-Oct-2024
 */
@SpringBootTest
@AutoConfigureMockMvc
public class AuthorControllerTest {

    @MockBean
    AuthorServiceImpl authorService;

    @Autowired
    MockMvc mockMvc;

    ObjectMapper objectMapper;
    private String API_URL = "/v1/authors";

    @BeforeEach
    void setUp() {
        this.objectMapper = new ObjectMapper();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /**
     * Tests that a page of authors is returned when calling {@code GET /v1/authors}.
     * Verifies that the size of the list is 2 and that the second author has ID 2.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldReturnPageAuthorWhenGetAuthorsCalled() throws Exception {
        when(authorService.findAllPageable(any(Pageable.class))).thenReturn(Data.PAGE_AUTHORS);

        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL)
                        .param("page", "0")
                        .param("size","10")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.authorList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.authorList[1].id").value(2));
        verify(authorService).findAllPageable(any(Pageable.class));
    }

    /**
     * Tests that a specific author is returned when calling {@code GET /v1/authors/{id}}.
     * Verifies that the author has ID 1 and first name "Gabriel".
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldReturnAuthorWhenGetAuthorByIdCalled() throws Exception {
        when(authorService.findById(anyLong())).thenReturn(Data.AUTHOR);
        mockMvc.perform(
                MockMvcRequestBuilders.get(API_URL + "/1")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.firstname").value("Gabriel"));
        verify(authorService).findById(anyLong());
    }

    /**
     * Tests that a new author is created when calling {@code POST /v1/authors/save-author}.
     * Verifies that the created author has ID 1.
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldReturnAuthorWhenSaveAuthorCalled() throws Exception {
        when(authorService.save(any(Author.class))).thenReturn(Data.AUTHOR);

        mockMvc.perform(
                MockMvcRequestBuilders.post(API_URL + "/save-author")
                        .content(objectMapper.writeValueAsString(Data.AUTHOR))
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
        verify(authorService).save(any(Author.class));
    }

    /**
     * Tests that an author is deleted when calling {@code DELETE /v1/authors/delete-author/{id}}.
     * Verifies that the operation is successful (status code 200 OK).
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldDeleteAuthorWhenDeleteAuthorById() throws Exception {
        doNothing().when(authorService).deleteById(anyLong());

        mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL + "/delete-author/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk());
    }

    /**
     * Tests that an author is updated when calling {@code POST /v1/authors/update-author}.
     * Verifies that the updated author has ID 1.
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldReturnAuthorWhenUpdateAuthorCalled() throws Exception {
        when(authorService.save(any(Author.class))).thenReturn(Data.AUTHOR);
        when(authorService.findById(anyLong())).thenReturn(Data.AUTHOR);

        mockMvc.perform(
                        MockMvcRequestBuilders.put(API_URL + "/update-author/1")
                                .content(objectMapper.writeValueAsString(Data.AUTHOR))
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L));
        verify(authorService).save(any(Author.class));
    }
}
