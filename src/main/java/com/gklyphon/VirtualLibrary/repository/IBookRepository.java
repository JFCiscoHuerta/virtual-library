package com.gklyphon.VirtualLibrary.repository;

import com.gklyphon.VirtualLibrary.model.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for managing Book entities.
 * Provides basic CRUD operations and custom query methods to
 * retrieve books by ISBN or title.
 *
 * This interface extends {@link JpaRepository}, which offers
 * a variety of database operations, including pagination and sorting.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
public interface IBookRepository extends JpaRepository<Book, Long> {

    /**
     * Retrieves a book by its ISBN.
     *
     * @param isbn the ISBN of the book to be retrieved
     * @return an {@link Optional} containing the found book or empty if not found
     */
    Optional<Book> findByIsbn(String isbn);

    /**
     * Retrieves a book by its title.
     *
     * @param title the title of the book to be retrieved
     * @return an {@link Optional} containing the found book or empty if not found
     */
    Optional<Book> findByTitle(String title);
}
