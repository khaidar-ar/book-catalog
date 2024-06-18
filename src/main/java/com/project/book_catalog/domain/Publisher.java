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
@Table(name = "publisher",indexes = {
        @Index(name = "publisher_secure_id",columnList = "secure_id")
})
public class Publisher extends AbstractBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "publisher_generator")
    @SequenceGenerator(name = "publisher_generator",sequenceName = "publisher_id_seq")
    private Long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "company_name",nullable = false)
    private String companyName;

    @Column(name = "address",nullable = true)
    private String address;

    @OneToMany(mappedBy = "publisher")
    private List<Book> books;
}
