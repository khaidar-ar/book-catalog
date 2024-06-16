package com.project.book_catalog.controller;

import com.project.book_catalog.domain.Author;
import com.project.book_catalog.dto.request.AuthorRequestDTO;
import com.project.book_catalog.dto.response.AuthorResponseDTO;
import com.project.book_catalog.service.AuthorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorResponseDTO>> findAll() {
        return ResponseEntity.ok().body(authorService.findAll());
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok().body(authorService.findById(id));
    }

    @PostMapping("/author")
    public ResponseEntity<String> create(@RequestBody @Valid AuthorRequestDTO authorRequestDTO) {
        authorService.create(authorRequestDTO);
        return ResponseEntity.ok().body("Success create author with name : " + authorRequestDTO.getName());
    }

    @PostMapping("/authors")
    public ResponseEntity<String> createAll(@RequestBody @Valid List<AuthorRequestDTO> authorRequestDTO) {
        authorService.createBatch(authorRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/author/{id}")
    public ResponseEntity<AuthorRequestDTO> update(@PathVariable Long id,
                                                   @RequestBody @Valid AuthorRequestDTO authorRequestDTO) {
        authorService.update(id, authorRequestDTO);
        return ResponseEntity.ok().body(authorRequestDTO);
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<String> softDelete(@PathVariable Long id) {
        AuthorResponseDTO authorResponseDTO = authorService.softDelete(id);
        return ResponseEntity.ok().body("Success delete author with name : " + authorResponseDTO.getName());
    }

    @DeleteMapping("/author-delete/{id}")
    public ResponseEntity<AuthorResponseDTO> delete(@PathVariable Long id) {
        AuthorResponseDTO authorResponseDTO = authorService.delete(id);
        return ResponseEntity.ok().body(authorResponseDTO);
    }
}
