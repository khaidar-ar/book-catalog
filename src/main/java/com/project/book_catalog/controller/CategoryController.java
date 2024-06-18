package com.project.book_catalog.controller;

import com.project.book_catalog.dto.request.CategoryRequestDTO;
import com.project.book_catalog.dto.response.CategoryResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;
import com.project.book_catalog.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<?> create(@ModelAttribute @Valid CategoryRequestDTO categoryRequestDTO) {
        categoryService.createAndUpdate(categoryRequestDTO);
        return ResponseEntity.ok().body("Success create category\n" + categoryRequestDTO);
    }

    @GetMapping("/categories")
    public ResponseEntity<ResponsePageDTO<CategoryResponseDTO>> findAll(
            @RequestParam(name = "pages", defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", defaultValue = "10") Integer limit,
            @RequestParam(name = "sortBy",defaultValue = "name") String sortBy,
            @RequestParam(name = "direction",defaultValue = "asc") String direction,
            @RequestParam(name = "filter",required = false) String name) {
        return ResponseEntity.ok().body(categoryService.findAll(pages, limit, sortBy, direction, name));
    }
}
