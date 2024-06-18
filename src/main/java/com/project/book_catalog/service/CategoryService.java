package com.project.book_catalog.service;

import com.project.book_catalog.dto.request.CategoryRequestDTO;
import com.project.book_catalog.dto.response.CategoryResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;

public interface CategoryService {

    public void createAndUpdate(CategoryRequestDTO categoryRequestDTO);

    public ResponsePageDTO<CategoryResponseDTO> findAll(Integer page,
                                                        Integer limit,
                                                        String sortBy,
                                                        String direction,
                                                        String name);
}
