package com.gklyphon.VirtualLibrary.service.impl;

import com.gklyphon.VirtualLibrary.exception.custom.ElementNotFoundException;
import com.gklyphon.VirtualLibrary.model.entity.Book;
import com.gklyphon.VirtualLibrary.repository.IBookRepository;
import com.gklyphon.VirtualLibrary.service.IBookService;
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

    @Override
    @Transactional(readOnly = true)
    public Book findByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn)
                .orElseThrow(() -> new ElementNotFoundException("Book with isbn: " + isbn + " not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title)
                .orElseThrow(() -> new ElementNotFoundException("Book with title: " + title + " not found."));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Book> findAllPageable(Pageable pageable) {
        return bookRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Book with id: " + id + " not found."));
    }

    @Override
    @Transactional
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new ElementNotFoundException("Book with id: " + id + " not found.");
        }
        bookRepository.deleteById(id);
    }
}
