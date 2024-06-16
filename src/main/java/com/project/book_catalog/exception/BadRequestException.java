package com.project.book_catalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.Serializable;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException implements Serializable {

    private static final long serialVersionUID = 8845969540355301214L;

    public BadRequestException(String message) {
        super(message);
    }
}
