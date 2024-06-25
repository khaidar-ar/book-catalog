package com.project.book_catalog.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
public class LoginRequestDTO {

    // @NotBlank
    private String username;

    // @NotBlank
    private String password;

}
