package com.gklyphon.VirtualLibrary.service.impl;


import com.gklyphon.VirtualLibrary.Data;
import com.gklyphon.VirtualLibrary.model.entity.Author;
import com.gklyphon.VirtualLibrary.model.entity.Book;
import com.gklyphon.VirtualLibrary.repository.IAuthorRepository;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the {@link AuthorServiceImpl} class.
 * This class uses Mockito to mock the dependencies and
 * test the behavior of the AuthorServiceImpl.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 22-Oct-2024
 */
@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock
    IAuthorRepository authorRepository;

    @InjectMocks
    AuthorServiceImpl authorService;

    /**
     * Test to ensure that an author can be found by ID.
     * It mocks the repository behavior and verifies the expected results.
     */
    @Test
    void shouldFindAuthorByIdWhenCalled() {
        when(authorRepository.findById(anyLong())).thenReturn(Optional.of(Data.AUTHOR));
        Author author = authorService.findById(1L);

        assertNotNull(author);
        assertEquals(Data.AUTHOR.getBirthdate(), author.getBirthdate());
        assertEquals(1L, author.getId());
        verify(authorRepository).findById(anyLong());
    }

    /**
     * Test to ensure that all authors can be retrieved.
     * It mocks the repository behavior and verifies that the
     * list of authors is not empty and contains the expected number of authors.
     */
    @Test
    void shouldFindAllAuthors() {
        when(authorRepository.findAll()).thenReturn(Data.AUTHORS);
        List<Author> authors = authorRepository.findAll();
        assertFalse(authors.isEmpty());
        assertEquals(2, authors.size());
        assertEquals(1L, authors.getFirst().getId());
        verify(authorRepository).findAll();
    }

    /**
     * Test to ensure that all authors can be retrieved in a paginated manner.
     * It verifies that the correct page size and content are returned.
     */
    @Test
    void shouldFindAllPageAuthors() {
        Pageable pageable = mock(Pageable.class);
        when(authorRepository.findAll(pageable)).thenReturn(Data.PAGE_AUTHORS);
        Page<Author> pageCalled = authorService.findAllPageable(pageable);

        assertThat(pageCalled.getContent()).hasSize(2);
        AssertionsForClassTypes.assertThat(pageCalled.getContent().getFirst().getFirstname()).isEqualTo("Gabriel");
        verify(authorRepository).findAll(pageable);
    }

    /**
     * Test to ensure that an author can be saved.
     * It mocks the repository behavior and verifies that the saved author
     * matches the expected data.
     */
    @Test
    void shouldSaveAuthor() {
        when(authorRepository.save(any(Author.class))).thenReturn(Data.AUTHOR);
        Author authorCalled = authorService.save(Data.AUTHOR);
        assertNotNull(authorCalled);
        assertEquals("Gabriel", authorCalled.getFirstname());
        verify(authorRepository).save(any(Author.class));
    }
}
