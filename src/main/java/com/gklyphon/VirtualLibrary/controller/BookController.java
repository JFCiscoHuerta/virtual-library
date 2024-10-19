package com.gklyphon.VirtualLibrary.controller;

import com.gklyphon.VirtualLibrary.model.entity.Book;
import com.gklyphon.VirtualLibrary.service.IBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Book entities.
 * Provides endpoints for CRUD operations on books.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
@RestController
@RequestMapping("/v1/books")
public class BookController {

    private final IBookService bookService;
    private final PagedResourcesAssembler<Book> pagedResourcesAssembler;

    @Autowired
    public BookController(IBookService bookService, PagedResourcesAssembler<Book> pagedResourcesAssembler) {
        this.bookService = bookService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Retrieves a paginated list of all books.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the size of the page (default is 10)
     * @return a ResponseEntity containing a paginated model of books
     */
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Book>>> getAllBooks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookService.findAllPageable(pageable);
        PagedModel<EntityModel<Book>> pagedModel = pagedResourcesAssembler.toModel(books);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Retrieves a book by its unique identifier.
     *
     * @param id the unique identifier of the book
     * @return a ResponseEntity containing the book if found, or NO_CONTENT if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(name = "id") Long id) {
        Book book = bookService.findById(id);
        return book != null ? new ResponseEntity<>(book, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves a book by its title.
     *
     * @param title the title of the book
     * @return a ResponseEntity containing the book if found, or NO_CONTENT if not found
     */
    @GetMapping("/by-title")
    public ResponseEntity<Book> getBookByTitle(@RequestParam(name = "title") String title) {
        Book book = bookService.findByTitle(title);
        return book != null ? new ResponseEntity<>(book, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves a book by its ISBN.
     *
     * @param isbn the ISBN of the book
     * @return a ResponseEntity containing the book if found, or NO_CONTENT if not found
     */
    @GetMapping("/by-isbn")
    public ResponseEntity<Book> getBookByIsbn(@RequestParam(name = "isbn") String isbn) {
        Book book = bookService.findByIsbn(isbn);
        return book != null ? new ResponseEntity<>(book, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Saves a new book to the database.
     *
     * @param book the book to be saved
     * @return a ResponseEntity containing the saved book or NO_CONTENT if the book is null
     */
    @PostMapping("/save-book")
    public ResponseEntity<?> saveBook(@RequestBody Book book) {
        if (book!=null) {
            Book bookSaved = bookService.save(book);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes a book by its unique identifier.
     *
     * @param id the unique identifier of the book to be deleted
     * @return a ResponseEntity indicating the status of the deletion
     */
    @DeleteMapping("/delete-book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable(name = "id") Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
