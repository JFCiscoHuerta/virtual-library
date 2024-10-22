package com.gklyphon.VirtualLibrary.controller;

import com.gklyphon.VirtualLibrary.model.entity.Author;
import com.gklyphon.VirtualLibrary.model.entity.Book;
import com.gklyphon.VirtualLibrary.service.IAuthorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
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
 * REST controller for managing authors.
 * Provides endpoints to create, retrieve, update, and delete authors.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 22-Oct-2024
 */
@RestController
@RequestMapping("/v1/authors")
public class AuthorController {

    private final IAuthorService authorService;
    private final PagedResourcesAssembler<Author> pagedResourcesAssembler;

    public AuthorController(IAuthorService authorService, PagedResourcesAssembler<Author> pagedResourcesAssembler) {
        this.authorService = authorService;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Retrieves a paginated list of authors.
     *
     * @param page the page number to retrieve (default is 0)
     * @param size the size of the page to retrieve (default is 10)
     * @return a ResponseEntity containing a PagedModel of authors
     */
    @Operation(summary = "Retrieve a paginated list of authors",
            description = "Retrieves a paginated list of all authors available in the library.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the list of authors.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "404", description = "No author found.")
    })
    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<Author>>> getAllAuthors(
            @Parameter(description = "The page number to retrieve (default is 0)",
                    required = false, example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Tha size of the page to retrieve (default is 10)",
                    required = false, example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Author> authors = authorService.findAllPageable(pageable);
        PagedModel<EntityModel<Author>> pagedModel = pagedResourcesAssembler.toModel(authors);
        return new ResponseEntity<>(pagedModel, HttpStatus.OK);
    }

    /**
     * Retrieves an author by their unique identifier.
     *
     * @param id the unique identifier of the author to retrieve
     * @return a ResponseEntity containing the author if found, otherwise NO_CONTENT
     */
    @Operation(summary = "Retrieve an author by ID",
            description = "Fetches an author using its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the author.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "204", description = "No author found for the provided ID.")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(
            @Parameter(description = "Unique identifier of the author to retrieve")
            @PathVariable(name = "id") Long id) {
        Author author = authorService.findById(id);
        return author != null ? new ResponseEntity<>(author, HttpStatus.OK) : new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Saves a new author.
     *
     * @param author the Author object to save
     * @return a ResponseEntity indicating the status of the save operation
     */
    @Operation(summary = "Save a new author",
            description = "Stores a new author in the database.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Author successfully created.",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Book.class))}),
            @ApiResponse(responseCode = "204", description = "No author provided in the request.")
    })
    @PostMapping("/save-author")
    public ResponseEntity<?> saveAuthor(
            @Parameter(description = "Author object to save")
            @Valid @RequestBody Author author, BindingResult result) {
        if (!result.hasErrors()) {
            Author authorSaved = authorService.save(author);
            return new ResponseEntity<>(author, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Deletes an author by their unique identifier.
     *
     * @param id the unique identifier of the author to delete
     * @return a ResponseEntity indicating the status of the delete operation
     */
    @Operation(summary = "Delete an author by ID",
            description = "Removes an author from the database using its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Author successfully deleted."),
            @ApiResponse(responseCode = "404", description = "Author not found for the provided ID.")
    })
    @DeleteMapping("/delete-author/{id}")
    public ResponseEntity<?> deleteAuthor(
            @Parameter(description = "Unique identifier of the author to delete")
            @PathVariable(name = "id") Long id
    ){
        authorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
