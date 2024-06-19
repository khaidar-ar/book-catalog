package com.project.book_catalog.controller;

import com.project.book_catalog.dto.request.BookRequestDTO;
import com.project.book_catalog.dto.response.BookDetailResponseDTO;
import com.project.book_catalog.dto.response.BookResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;
import com.project.book_catalog.service.BookService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class BookController {

    private final BookService bookService;

    @PostMapping("/book")
    public ResponseEntity<?> create(@ModelAttribute @Valid BookRequestDTO bookRequestDTO) {
        bookService.create(bookRequestDTO);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/books")
    public ResponseEntity<ResponsePageDTO<BookResponseDTO>> getAll(
            @RequestParam(name = "pages", defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", defaultValue = "title") String sortBy,
            @RequestParam(name = "direction", defaultValue = "asc") String direction,
            @RequestParam(name = "name", required = false) String name) {
        ResponsePageDTO<BookResponseDTO> result = bookService.findBooks(pages, limit, sortBy, direction, name);
        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDetailResponseDTO> getBookDetail(@PathVariable String id) {
        return ResponseEntity.ok().body(bookService.findBookDetail(id));
    }
}
