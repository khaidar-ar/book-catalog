package com.project.book_catalog.repository;

import com.project.book_catalog.domain.Author;
import com.project.book_catalog.dto.response.AuthorResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    public Optional<Author> findBySecureId(String id);

    @Query("SELECT a FROM Author a WHERE UPPER(a.name) LIKE UPPER(%:name%) AND a.deleted = false OR a.deleted IS NULL")
    public Page<Author> findByNameLikeIgnoreCase(@Param("name") String name, Pageable pageable);

    public List<Author> findBySecureIdIn(List<String> id);
}
