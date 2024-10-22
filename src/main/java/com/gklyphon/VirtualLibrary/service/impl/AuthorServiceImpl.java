package com.gklyphon.VirtualLibrary.service.impl;

import com.gklyphon.VirtualLibrary.exception.custom.ElementNotFoundException;
import com.gklyphon.VirtualLibrary.model.entity.Author;
import com.gklyphon.VirtualLibrary.repository.IAuthorRepository;
import com.gklyphon.VirtualLibrary.service.IAuthorService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the IAuthorService interface for managing authors.
 * This service provides methods to perform CRUD operations on authors.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 22-Oct-2024
 */
@Service
public class AuthorServiceImpl implements IAuthorService {

    private final IAuthorRepository authorRepository;
    private final PagedResourcesAssembler<Author> pagedResourcesAssembler;

    public AuthorServiceImpl(IAuthorRepository authorRepository, PagedResourcesAssembler<Author> pagedResourcesAssembler) {
        this.authorRepository = authorRepository;
        this.pagedResourcesAssembler = pagedResourcesAssembler;
    }

    /**
     * Retrieves an author by their unique identifier.
     *
     * @param id the unique identifier of the author
     * @return the Author object
     * @throws ElementNotFoundException if the author is not found
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "authors", key = "#id")
    public Author findById(Long id) {
        return authorRepository.findById(id).orElseThrow(
                () -> new ElementNotFoundException("Author with id: " + id + " not found."));
    }

    /**
     * Retrieves all authors.
     *
     * @return a list of Author objects
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "authors")
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    /**
     * Retrieves a paginated list of authors.
     *
     * @param pageable the pagination information
     * @return a Page containing Author objects
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "authorsPage", key = "#pageable.pageNumber + '-' + #pageable.pageSize")
    public Page<Author> findAllPageable(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    /**
     * Saves a new author or updates an existing one.
     *
     * @param author the Author object to save
     * @return the saved Author object
     */
    @Override
    @Transactional
    @CachePut(value = "authors", key = "#author.id")
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    /**
     * Deletes an author by their unique identifier.
     *
     * @param id the unique identifier of the author to delete
     * @throws ElementNotFoundException if the author is not found
     */
    @Override
    @Transactional
    @CacheEvict(value = "authors", key = "#id")
    public void deleteById(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ElementNotFoundException("Author with id: " + id + " not found.");
        }
        authorRepository.deleteById(id);
    }
}
