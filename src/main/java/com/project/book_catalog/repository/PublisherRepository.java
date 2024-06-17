package com.project.book_catalog.repository;

import com.project.book_catalog.domain.Publisher;
import com.project.book_catalog.dto.response.PublisherResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {

    public Optional<Publisher> findBySecureId(String id);

    public Page<Publisher> findByNameLikeIgnoreCase(String name, Pageable pageable);
}
