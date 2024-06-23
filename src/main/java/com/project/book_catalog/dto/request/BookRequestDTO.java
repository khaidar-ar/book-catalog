package com.project.book_catalog.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class BookRequestDTO {

    @NotBlank
    private String title;

    @JsonProperty(namespace = "synopsis")
    private String description;

    @NotEmpty
    private List<String> authors;

    @NotEmpty
    private List<String> categories;

    @NotEmpty
    private String publisherId;
}
