package com.gklyphon.VirtualLibrary.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
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
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Author extends Auditable implements Serializable {

    @Serial
    private static final long serialVersionUID = 6529685098267757690L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private String country;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Book> books;
}
