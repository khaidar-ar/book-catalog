package com.project.book_catalog.repository;

import com.project.book_catalog.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BookRepository extends JpaRepository<Book,String> {;

    @Query("SELECT b FROM Book b WHERE UPPER (b.title) LIKE UPPER (%:title%) AND b.deleted = false OR b.deleted IS NULL")
    public Page<Book> findByTitleLikeIgnoreCase(@Param("title") String title, Pageable pageable);

    public Optional<Book> findBySecureId(String id);
}
