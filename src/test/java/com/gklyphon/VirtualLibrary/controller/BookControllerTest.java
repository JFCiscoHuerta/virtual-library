package com.gklyphon.VirtualLibrary.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gklyphon.VirtualLibrary.Data;
import com.gklyphon.VirtualLibrary.exception.custom.ElementNotFoundException;
import com.gklyphon.VirtualLibrary.model.entity.Book;
import com.gklyphon.VirtualLibrary.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Unit test for the BookController class.
 * This class tests the endpoints defined in the BookController
 * to ensure they function as expected using MockMvc.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
@AutoConfigureMockMvc
@WebMvcTest(BookController.class)
class BookControllerTest {

    @MockBean
    BookServiceImpl bookService;

    @Autowired
    MockMvc mockMvc;

    final String API_URL = "/v1/books";
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
    }

    /**
     * Tests the endpoint to retrieve a paginated list of books.
     * Verifies that the response contains the expected number of books.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldReturnPageBookWhenGetBooksCalled() throws Exception {
        when(bookService.findAllPageable(any(Pageable.class))).thenReturn(Data.PAGE_BOOKS);

        mockMvc.perform(MockMvcRequestBuilders.get(API_URL)
                        .param("page", "0")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$._embedded.bookList", hasSize(2)))
                .andExpect(jsonPath("$._embedded.bookList[0].id").value(1))
                .andExpect(jsonPath("$._embedded.bookList[1].id").value(2));
        verify(bookService).findAllPageable(any(Pageable.class));
    }

    /**
     * Tests the endpoint to retrieve a book by its ID.
     * Verifies that the returned book matches the expected ID.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldReturnBookWhenGetBookByIdCalled() throws Exception {
        when(bookService.findById(anyLong())).thenReturn(Data.BOOK);
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
        verify(bookService).findById(anyLong());
    }

    /**
     * Tests the endpoint to retrieve a book by its title.
     * Verifies that the returned book matches the expected title.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldReturnBookWhenGetBookByTitleCalled() throws Exception {
        when(bookService.findByTitle(anyString())).thenReturn(Data.BOOK);
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/by-title")
                        .param("title","Book1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book1"));
        verify(bookService).findByTitle(anyString());
    }

    /**
     * Tests the endpoint to retrieve a book by its ISBN.
     * Verifies that the returned book matches the expected ISBN.
     */
    @Test
    @WithMockUser(username = "USER", roles = "USER")
    void shouldReturnBookWhenGetBookByIsbnCalled() throws Exception {
        when(bookService.findByIsbn(anyString())).thenReturn(Data.BOOK);
        mockMvc.perform(MockMvcRequestBuilders.get(API_URL + "/by-isbn")
                        .param("isbn","ISBN1")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("ISBN1"));
        verify(bookService).findByIsbn(anyString());
    }

    /**
     * Tests the endpoint to save a new book.
     * Verifies that the returned book matches the expected ISBN.
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldReturnBookWhenSaveBookCalled() throws Exception {
        when(bookService.save(any(Book.class))).thenReturn(Data.BOOK);
        mockMvc.perform(MockMvcRequestBuilders.post(API_URL + "/save-book")
                        .content(objectMapper.writeValueAsString(Data.BOOK))
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.isbn").value("ISBN1"));
        verify(bookService).save(any(Book.class));
    }

    /**
     * Tests the endpoint to delete a book by its ID.
     * Verifies that the delete operation is successful.
     */
    @Test
    @WithMockUser(username = "ADMIN", roles = "ADMIN")
    void shouldDeleteBookWhenDeleteBookById() throws Exception {
        doNothing().when(bookService).deleteById(anyLong());
        doThrow(ElementNotFoundException.class).when(bookService).findById(1L);
        mockMvc.perform(
                MockMvcRequestBuilders.delete(API_URL + "/delete-book/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(csrf())
        )
                .andExpect(status().isOk());
    }

}