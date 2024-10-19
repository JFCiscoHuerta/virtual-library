package com.gklyphon.VirtualLibrary.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Generic service interface for managing entities.
 * Provides common CRUD operations for any entity type T.
 *
 * @param <T> the type of the entity managed by this service
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
public interface IService <T>{

    /**
     * Retrieves an entity by its unique identifier.
     *
     * @param id the unique identifier of the entity to be retrieved
     * @return the entity if found, or null if not found
     */
    T findById(Long id);

    /**
     * Retrieves all entities of type T.
     *
     * @return a list of all entities
     */
    List<T> findAll();

    /**
     * Retrieves all entities of type T in a paginated format.
     *
     * @param pageable the pagination information
     * @return a page of entities
     */
    Page<T> findAllPageable(Pageable pageable);

    /**
     * Saves the provided entity to the database.
     *
     * @param t the entity to be saved
     * @return the saved entity
     */
    T save(T t);

    /**
     * Deletes the entity with the specified unique identifier.
     *
     * @param id the unique identifier of the entity to be deleted
     */
    void deleteById(Long id);

}
