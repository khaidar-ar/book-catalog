package com.project.book_catalog.controller;

import com.project.book_catalog.dto.request.AuthorRequestDTO;
import com.project.book_catalog.dto.response.AuthorResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;
import com.project.book_catalog.service.AuthorService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<ResponsePageDTO<AuthorResponseDTO>> findAll(
            @RequestParam(name = "pages", required = true, defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy", required = true, defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
            @RequestParam(name = "name", required = false) String name
    ) {
        return ResponseEntity.ok().body(authorService.findAll(pages, limit, sortBy, direction, name));
    }

    @GetMapping("/author/{id}")
    public ResponseEntity<AuthorResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(authorService.findBySecureId(id));
    }

    @PostMapping("/author")
    public ResponseEntity<?> create(@ModelAttribute @Valid AuthorRequestDTO authorRequestDTO) {
        authorService.create(authorRequestDTO);
        return ResponseEntity.ok().body("Success create author\n" + authorRequestDTO);
    }

    @PostMapping("/authors")
    public ResponseEntity<?> createAll(@RequestBody @Valid List<AuthorRequestDTO> authorRequestDTO) {
        authorService.createBatch(authorRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/author/{id}")
    public ResponseEntity<?> update(@PathVariable String id,
                                    @ModelAttribute @Valid AuthorRequestDTO authorRequestDTO) {
        authorService.update(id, authorRequestDTO);
        return ResponseEntity.ok().body("Success update \n" + authorRequestDTO);
    }

    @DeleteMapping("/author/{id}")
    public ResponseEntity<?> softDelete(@PathVariable String id) {
        AuthorResponseDTO authorResponseDTO = authorService.softDelete(id);
        return ResponseEntity.ok().body("Success delete author\n" + authorResponseDTO);
    }

    @DeleteMapping("/author-delete/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        AuthorResponseDTO authorResponseDTO = authorService.delete(id);
        return ResponseEntity.ok().body("Success delete author\n" + authorResponseDTO);
    }
}
