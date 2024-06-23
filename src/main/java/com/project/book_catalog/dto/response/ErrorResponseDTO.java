package com.project.book_catalog.dto.response;

import java.util.Date;
import java.util.List;
import org.springframework.http.HttpStatus;
import com.project.book_catalog.enums.ErrorCode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponseDTO {
    
    private Date timestamp;

    private String messageString;

    private ErrorCode ErrorCode;

    private List<String> details;

    private HttpStatus status;
}
