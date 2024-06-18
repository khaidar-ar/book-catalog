package com.project.book_catalog.service;

import com.project.book_catalog.dto.request.AuthorRequestDTO;
import com.project.book_catalog.dto.response.AuthorResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;

import java.util.List;

public interface AuthorService {

    public AuthorResponseDTO findBySecureId(String id);

    public ResponsePageDTO<AuthorResponseDTO> findAll(Integer pages,
                                                      Integer limit,
                                                      String sortBy,
                                                      String direction,
                                                      String name);

    public void create(AuthorRequestDTO author);

    public void createBatch(List<AuthorRequestDTO> authorRequestDTOList);

    public void update(String id, AuthorRequestDTO author);

    public AuthorResponseDTO delete(String id);

    public AuthorResponseDTO softDelete(String id);
}
