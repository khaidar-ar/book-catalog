package com.project.book_catalog.security.model;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JwtToken implements Token {

    private final String raw;

    private Claims claims;

    @Override
    public String getToken() {
        return this.raw;
    }

}
