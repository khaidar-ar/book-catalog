package com.project.book_catalog.repository;

import com.project.book_catalog.domain.Publisher;
import com.project.book_catalog.dto.response.PublisherResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    public Optional<Publisher> findBySecureId(String id);

    @Query("SELECT p FROM Publisher p WHERE UPPER(p.name) LIKE UPPER(%:name%) AND p.deleted = false OR p.deleted IS " +
            "NULL")
    public Page<Publisher> findByNameLikeIgnoreCase(@Param("name") String name, Pageable pageable);

    public List<Publisher> findBySecureIdIn(List<String> id);
}
