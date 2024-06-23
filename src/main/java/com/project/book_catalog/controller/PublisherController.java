package com.project.book_catalog.controller;

import com.project.book_catalog.dto.request.PublisherRequestDTO;
import com.project.book_catalog.dto.request.PublisherUpdateRequestDTO;
import com.project.book_catalog.dto.response.PublisherResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;
import com.project.book_catalog.service.PublisherService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
public class PublisherController {

    private PublisherService publisherService;

    @GetMapping("/publishers")
    public ResponseEntity<ResponsePageDTO<PublisherResponseDTO>> findAll(
            @RequestParam(name = "pages", required = true, defaultValue = "0") Integer pages,
            @RequestParam(name = "limit", required = true, defaultValue = "10") Integer limit,
            @RequestParam(name = "sort-by", required = true, defaultValue = "name") String sortBy,
            @RequestParam(name = "direction", required = true, defaultValue = "asc") String direction,
            @RequestParam(name = "filter", required = false) String filter) {
        return ResponseEntity.ok().body(publisherService.findAll(pages,limit,sortBy,direction,filter));
    }

    @GetMapping(value = "/publisher/{id}")
    public ResponseEntity<PublisherResponseDTO> findById(@PathVariable String id) {
        return ResponseEntity.ok().body(publisherService.findById(id));
    }

    @PostMapping(value = "/publisher")
    public ResponseEntity<Void> create(@RequestBody @Valid PublisherRequestDTO publisherRequestDTO) {
        publisherService.create(publisherRequestDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/publisher/{id}")
    public ResponseEntity<PublisherResponseDTO> update(@PathVariable String id,
                                                       @RequestBody @Valid PublisherUpdateRequestDTO publisherUpdateRequestDTO) {
        PublisherResponseDTO res = publisherService.update(id, publisherUpdateRequestDTO);
        return ResponseEntity.ok().body(res);
    }
}
