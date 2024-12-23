package com.gklyphon.VirtualLibrary.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Represents a Book entity for the Virtual Library system.
 * This class maps to the "books" table in the database.
 * Each book has an ID, title, ISBN, and price, with validations to ensure data integrity.
 *
 * <p>The class is annotated with Lombok annotations for boilerplate code reduction:
 * - {@code @Data} generates getters, setters, equals, hashCode, and toString methods.
 * - {@code @AllArgsConstructor} generates a constructor with all fields.
 * - {@code @NoArgsConstructor} generates a no-arguments constructor.</p>
 *
 * <p>Data Integrity Constraints:
 * - The ISBN field is unique across all book entries.</p>
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 18-Oct-2024
 */
@Entity
@Table(name = "books" ,uniqueConstraints = {
        @UniqueConstraint(columnNames = "isbn")
})
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Auditable implements Serializable {

    @Serial
    private static final long serialVersionUID = -8019788932459579321L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String isbn;

    private BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;
}
