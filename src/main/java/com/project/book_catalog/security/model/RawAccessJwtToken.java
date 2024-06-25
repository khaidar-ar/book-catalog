package com.project.book_catalog.security.model;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class RawAccessJwtToken implements Token {

    private String token;

    public Jws<Claims> parseClaims(SecretKey key){
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(this.token);
    }

    @Override
    public String getToken() {
        return this.token;
    }

}
