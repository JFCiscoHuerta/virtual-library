package com.gklyphon.VirtualLibrary.repository;

import com.gklyphon.VirtualLibrary.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing {@link Author} entities.
 * Provides methods to perform CRUD operations and interact with the database.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 22-Oct-2024
 */
public interface IAuthorRepository extends JpaRepository<Author, Long> {
}
