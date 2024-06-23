package com.project.book_catalog.dto.response;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.project.book_catalog.enums.ErrorCode;
import lombok.Data;

@Data
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ErrorResponseDTO {

    private Date timestamp;

    private String messageString;

    private ErrorCode errorCode;

    private List<String> details;

    private HttpStatus status;

    public ErrorResponseDTO(
            String message,
            ErrorCode errorCode,
            List<String> details, HttpStatus status) {
        this.timestamp = new Date();
        this.messageString = message;
        this.errorCode = errorCode;
        this.details = details;
        this.status = status;
    }

    public static ErrorResponseDTO of(final String message, List<String> details, final ErrorCode errorCode,
            HttpStatus status) {
        return new ErrorResponseDTO(message, errorCode, details, status);
    }
}
