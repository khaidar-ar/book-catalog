package com.project.book_catalog.exception;

import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.project.book_catalog.dto.response.ErrorResponseDTO;
import com.project.book_catalog.enums.ErrorCode;

@ControllerAdvice
public class ExceptionHandlerAdive extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {

        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            details.add(error.getObjectName() + " : " + error.getDefaultMessage());
        }
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.of("invalid data", details, ErrorCode.INVALID_DATA,
                HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }

    @ExceptionHandler(ResourceNotFound.class)
    public ResponseEntity<ErrorResponseDTO> handleResourceNotFount(ResourceNotFound ex, WebRequest request) {
        List<String> details = new ArrayList<>();
        details.add(ex.getMessage());
        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.of("resource not found", details,
                ErrorCode.RESOURCE_NOT_FOUND, HttpStatus.BAD_REQUEST);
        return ResponseEntity.badRequest().body(errorResponseDTO);
    }
}
