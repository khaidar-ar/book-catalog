package com.project.book_catalog.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "author_seq_generator")
    @SequenceGenerator(name = "author_seq_generator",sequenceName = "author_id_seq")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "birth_date",nullable = false)
    private LocalDate birthDate;

    @Column(name = "deleted", columnDefinition = "boolean default false")
    private Boolean deleted;
}
