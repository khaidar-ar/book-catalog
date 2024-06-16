package com.project.book_catalog.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_detail")
public class BookDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "settings")
    private String settings;

    @Column(name = "thumbnail")
    private String thumbnail;

    @OneToOne
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;
}
