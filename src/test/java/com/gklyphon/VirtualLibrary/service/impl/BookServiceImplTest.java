package com.gklyphon.VirtualLibrary.service.impl;

import com.gklyphon.VirtualLibrary.Data;
import com.gklyphon.VirtualLibrary.model.entity.Book;
import com.gklyphon.VirtualLibrary.repository.IBookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the BookServiceImpl class.
 * This class tests the various methods of the BookServiceImpl
 * using Mockito for mocking the IBookRepository.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private IBookRepository bookRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {

    }

    /**
     * Tests the findById method in BookServiceImpl.
     * Verifies that a book can be found by its ID when it exists.
     */
    @Test
    void shouldFindBookByIdWhenCalled() {
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(Data.BOOK));
        Book bookCalled = bookService.findById(1L);
        assertNotNull(bookCalled);
        assertEquals(1L, bookCalled.getId());
        verify(bookRepository).findById(anyLong());
    }

    /**
     * Tests the findByIsbn method in BookServiceImpl.
     * Verifies that a book can be found by its ISBN when it exists.
     */
    @Test
    void shouldFindBookByIsbnWhenCalled() {
        when(bookRepository.findByIsbn(anyString())).thenReturn(Optional.of(Data.BOOK));
        Book bookCalled = bookService.findByIsbn("ISBN1");
        assertNotNull(bookCalled);
        assertEquals("ISBN1", bookCalled.getIsbn());
        verify(bookRepository).findByIsbn(anyString());
    }

    /**
     * Tests the findByTitle method in BookServiceImpl.
     * Verifies that a book can be found by its title when it exists.
     */
    @Test
    void shouldFindBookByTitleWhenCalled() {
        when(bookRepository.findByTitle(anyString())).thenReturn(Optional.of(Data.BOOK));
        Book bookCalled = bookService.findByTitle("Book1");
        assertNotNull(bookCalled);
        assertEquals("Book1", bookCalled.getTitle());
        verify(bookRepository).findByTitle(anyString());
    }

    /**
     * Tests the findAll method in BookServiceImpl.
     * Verifies that all books can be retrieved and that the returned list is not empty.
     */
    @Test
    void shouldFindAllBooks() {
        when(bookRepository.findAll()).thenReturn(Data.BOOKS);
        List<Book> books = bookService.findAll();
        assertFalse(books.isEmpty());
        assertEquals(2, books.size());
        assertEquals("Book2", books.get(1).getTitle());
        verify(bookRepository).findAll();
    }

    /**
     * Tests the findAllPageable method in BookServiceImpl.
     * Verifies that a paginated list of books can be retrieved.
     */
    @Test
    void shouldFindAllPageBooks() {
        Pageable pageable = mock(Pageable.class);
        when(bookRepository.findAll(pageable)).thenReturn(Data.PAGE_BOOKS);
        Page<Book> pageCalled = bookService.findAllPageable(pageable);

        assertThat(pageCalled.getContent()).hasSize(2);
        assertThat(pageCalled.getContent().getFirst().getTitle()).isEqualTo("Book1");
        verify(bookRepository).findAll(pageable);
    }

    /**
     * Tests the save method in BookServiceImpl.
     * Verifies that a book can be saved successfully.
     */
    @Test
    void shouldSaveBook() {
        when(bookRepository.save(any(Book.class))).thenReturn(Data.BOOK);
        Book bookCalled = bookService.save(Data.BOOK);
        assertNotNull(bookCalled);;
        assertEquals("Book1", bookCalled.getTitle());
        verify(bookRepository).save(any(Book.class));
    }
    
}