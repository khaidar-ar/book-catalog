package com.project.book_catalog.service;

import com.project.book_catalog.dto.request.BookRequestDTO;
import com.project.book_catalog.dto.response.BookDetailResponseDTO;
import com.project.book_catalog.dto.response.BookResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;

public interface BookService {

    public void create(BookRequestDTO bookRequestDTO);

    public ResponsePageDTO<BookResponseDTO> findBooks(Integer pages,
                                                     Integer limit,
                                                     String sortBy,
                                                     String direction,
                                                     String name
    );

    public BookDetailResponseDTO findBookDetail(String id);
}
