package com.project.book_catalog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book",indexes = {
        @Index(name = "book_secure_id",columnList = "secure_id")
})
@Inheritance(strategy =InheritanceType.SINGLE_TABLE)
public class Book extends AbstractBaseEntity {

    @Id
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", nullable = true)
    private String description;

    @ManyToOne
    @JoinColumn(name = "publisher_id", nullable = false)
    private Publisher publisher;

    @ManyToMany
    @JoinTable(name = "book_author", joinColumns = {
            @JoinColumn(name = "book_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "author_id", referencedColumnName = "id")
    })
    private List<Author> authors;


    @ManyToMany
    @JoinTable(name = "book_category", joinColumns = {
            @JoinColumn(name = "book_id", referencedColumnName = "id")
    }, inverseJoinColumns = {
            @JoinColumn(name = "category_id", referencedColumnName = "code")
    })
    private List<Category> categories;

}
