package com.gklyphon.VirtualLibrary.service;

import com.gklyphon.VirtualLibrary.model.entity.Book;

/**
 * Service interface for managing Book entities.
 * Extends the generic IService interface to provide
 * additional methods specific to Book management.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
public interface IBookService extends IService<Book> {

    /**
     * Retrieves a book by its ISBN.
     *
     * @param isbn the ISBN of the book to be retrieved
     * @return the book if found, or null if not found
     */
    Book findByIsbn(String isbn);

    /**
     * Retrieves a book by its title.
     *
     * @param title the title of the book to be retrieved
     * @return the book if found, or null if not found
     */
    Book findByTitle(String title);
}
