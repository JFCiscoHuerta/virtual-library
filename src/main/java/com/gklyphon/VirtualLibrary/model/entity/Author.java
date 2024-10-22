package com.gklyphon.VirtualLibrary.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * Represents an author entity in the Virtual Library.
 * This class is mapped to the "authors" table in the database.
 *
 * @author JFCiscoHuerta
 * @version 1.0
 * @since 22-Oct-2024
 */
@Entity
@Table(name = "authors")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Author implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String country;
    
    //private Set<Book> books;
}
