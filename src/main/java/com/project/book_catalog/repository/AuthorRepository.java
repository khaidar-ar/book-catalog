package com.project.book_catalog.repository;

import com.project.book_catalog.domain.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    public Optional<Author> findBySecureId(String id);

    public Page<Author> findByNameLikeIgnoreCase(String name, Pageable pageable);

    public List<Author> findBySecureIdIn(List<String> id);

}
