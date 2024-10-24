package com.gklyphon.VirtualLibrary.controller;

import com.gklyphon.VirtualLibrary.model.entity.Author;
import com.gklyphon.VirtualLibrary.model.entity.Book;
import com.gklyphon.VirtualLibrary.service.IAuthorService;
import com.gklyphon.VirtualLibrary.service.IBookService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    private final IAuthorService authorService;
    private final PagedResourcesAssembler<Book> pagedResourcesAssembler;

    @Autowired
    public BookController(IAuthorService authorService, IBookService bookService, PagedResourcesAssembler<Book> pagedResourcesAssembler) {
        this.authorService = authorService;
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
    @Operation(summary = "Get All Books",
            description = "Retrieves a paginated list of all books available in the library.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of books.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "No books found.")
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Book>>> getAllBooks(
            @Parameter(description = "The page number to retrieve (default is 0)",
                    required = false, example = "0")
            @RequestParam(defaultValue = "0", required = false) int page,
            @Parameter(description = "The size of the page to retrieve (default is 10)",
                    required = false,
                    example = "10")
            @RequestParam(defaultValue = "10", required = false) int size) {

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
    @Operation(summary = "Retrieve a Book by ID",
            description = "Fetches a book using its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the book.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "204", description = "No book found for the provided ID.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "Unique identifier of the book to retrieve")
            @PathVariable(name = "id") Long id) {
        Book book = bookService.findById(id);
        return book != null ? new ResponseEntity<>(book, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves a book by its title.
     *
     * @param title the title of the book
     * @return a ResponseEntity containing the book if found, or NO_CONTENT if not found
     */
    @Operation(summary = "Retrieve a Book by Title",
            description = "Fetches a book using its title.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the book.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "204", description = "No book found for the provided title.")
    })
    @GetMapping("/by-title")
    public ResponseEntity<Book> getBookByTitle(
            @Parameter(description = "Title of the book to retrieve")
            @RequestParam(name = "title") String title) {
        Book book = bookService.findByTitle(title);
        return book != null ? new ResponseEntity<>(book, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Retrieves a book by its ISBN.
     *
     * @param isbn the ISBN of the book
     * @return a ResponseEntity containing the book if found, or NO_CONTENT if not found
     */
    @Operation(summary = "Retrieve a Book by ISBN",
            description = "Fetches a book using its ISBN.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the book.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "204", description = "No book found for the provided ISBN.")
    })
    @GetMapping("/by-isbn")
    public ResponseEntity<Book> getBookByIsbn(
            @Parameter(description = "ISBN of the book to retrieve")
            @RequestParam(name = "isbn") String isbn) {
        Book book = bookService.findByIsbn(isbn);
        return book != null ? new ResponseEntity<>(book, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Saves a new book to the database.
     *
     * @param book the book to be saved
     * @return a ResponseEntity containing the saved book or NO_CONTENT if the book is null
     */
    @Operation(summary = "Save a New Book",
            description = "Stores a new book in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Book successfully created.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "204", description = "No book provided in the request.")
    })
    @PostMapping("/save-book")
    public ResponseEntity<?> saveBook(
            @Parameter(description = "Book object to save")
            @Valid @RequestBody Book book, BindingResult result,
            @RequestParam(name = "author_id", required = true) Long id) {
        if (!result.hasErrors()) {
            Author author = authorService.findById(id);
            book.setAuthor(author);
            Book bookSaved = bookService.save(book);
            return new ResponseEntity<>(book, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Deletes a book by its unique identifier.
     *
     * @param id the unique identifier of the book to be deleted
     * @return a ResponseEntity indicating the status of the deletion
     */
    @Operation(summary = "Delete a Book by ID",
            description = "Removes a book from the database using its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Book successfully deleted."),
            @ApiResponse(responseCode = "404", description = "Book not found for the provided ID.")
    })
    @DeleteMapping("/delete-book/{id}")
    public ResponseEntity<?> deleteBook(
            @Parameter(description = "Unique identifier of the book to delete")
            @PathVariable(name = "id") Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
