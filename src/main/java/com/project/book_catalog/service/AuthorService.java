package com.project.book_catalog.service;

import com.project.book_catalog.dto.request.AuthorRequestDTO;
import com.project.book_catalog.dto.response.AuthorResponseDTO;

import java.util.List;

public interface AuthorService {

    public AuthorResponseDTO findById(Long id);

    public List<AuthorResponseDTO> findAll();

    public void create(AuthorRequestDTO author);

    public void createBatch(List<AuthorRequestDTO> authorRequestDTOList);

    public void update(Long id, AuthorRequestDTO author);

    public AuthorResponseDTO delete(Long id);

    public AuthorResponseDTO softDelete(Long id);
}
