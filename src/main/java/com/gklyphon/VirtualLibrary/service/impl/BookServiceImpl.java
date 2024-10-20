package com.gklyphon.VirtualLibrary.service.impl;

import com.gklyphon.VirtualLibrary.exception.custom.ElementNotFoundException;
import com.gklyphon.VirtualLibrary.model.entity.Book;
import com.gklyphon.VirtualLibrary.repository.IBookRepository;
import com.gklyphon.VirtualLibrary.service.IBookService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * Implementation of the IBookService interface.
 * This class provides the business logic for managing Book entities.
 * It interacts with the IBookRepository for database operations.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
@Service
public class BookServiceImpl implements IBookService {

    private final IBookRepository bookRepository;

    public BookServiceImpl(IBookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Finds a Book entity by its ISBN.
     * This method is marked as read-only and caches the result for future use.
     *
     * @param isbn the ISBN of the book to find
     * @return the found Book entity
     * @throws ElementNotFoundException if no book with the given ISBN exists
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "books", key = "#isbn")
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ElementNotFoundException("Book with isbn: " + isbn + " not found."));
    }

    /**
     * Finds a Book entity by its title.
     * This method is marked as read-only and caches the result for future use.
     *
     * @param title the title of the book to find
     * @return the found Book entity
     * @throws ElementNotFoundException if no book with the given title exists
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "books", key = "#title")
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new ElementNotFoundException("Book with title: " + title + " not found."));
    }

    /**
     * Retrieves all Book entities.
     * This method is marked as read-only and caches the result for future use.
     *
     * @return a list of all Book entities
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "books")
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    /**
     * Retrieves a paginated list of Book entities.
     * This method is marked as read-only and caches the result for future use.
     *
     * @param pageable the pagination information
     * @return a page of Book entities
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "booksPage", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Book> findAllPageable(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    /**
     * Finds a Book entity by its ID.
     * This method is marked as read-only and caches the result for future use.
     *
     * @param id the ID of the book to find
     * @return the found Book entity
     * @throws ElementNotFoundException if no book with the given ID exists
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "books", key = "#id")
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Book with id: " + id + " not found."));
    }

    /**
     * Saves a new or existing Book entity.
     * This method caches the saved entity for future use.
     *
     * @param book the Book entity to save
     * @return the saved Book entity
     */
    @Override
    @Transactional
    @CachePut(value = "books", key = "#book.id")
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    /**
     * Deletes a Book entity by its ID.
     * This method removes the cached entity as well.
     *
     * @param id the ID of the book to delete
     * @throws ElementNotFoundException if no book with the given ID exists
     */
    @Override
    @Transactional
    @CacheEvict(value = "books", key = "#id")
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ElementNotFoundException("Book with id: " + id + " not found.");
        }
        bookRepository.deleteById(id);
    }
}
