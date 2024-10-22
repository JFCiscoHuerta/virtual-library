package com.gklyphon.VirtualLibrary;

import com.gklyphon.VirtualLibrary.model.entity.Author;
import com.gklyphon.VirtualLibrary.model.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * A utility class that provides test data for Book entities.
 * This class is used to define constant instances of Book and collections of Books for testing purposes.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
public class Data {

    public static final Book BOOK = new Book(1L, "Book1", "ISBN1", new BigDecimal("2500"));
    public static final List<Book> BOOKS = List.of(
            new Book(1L, "Book1", "ISBN1", new BigDecimal("2500")),
            new Book(2L, "Book2", "ISBN2", new BigDecimal("370")));
    public static final Page<Book> PAGE_BOOKS = new PageImpl<>(BOOKS);

    public static final Author AUTHOR = new Author(1L, "Gabriel", "García Márquez", LocalDate.of(1927, 3, 6), "Colombia");
    public static final List<Author> AUTHORS = List.of(
            new Author(1L, "Gabriel", "García Márquez", LocalDate.of(1927, 3, 6), "Colombia"),
            new Author(2L, "Jane", "Austen", LocalDate.of(1775, 12, 16), "United Kingdom"));
    public static final Page<Author> PAGE_AUTHORS = new PageImpl<>(AUTHORS);

}