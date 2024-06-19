package com.project.book_catalog.dto.response;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.project.book_catalog.domain.Publisher;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BookDetailResponseDTO {

    private String title;

    private PublisherResponseDTO publisherResponseDTO;

    private List<AuthorResponseDTO> authorResponseDTOS;

    private List<CategoryResponseDTO> categoryResponseDTOS;

    private String description;
}
