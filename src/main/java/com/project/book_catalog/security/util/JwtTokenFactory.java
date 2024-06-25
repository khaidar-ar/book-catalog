package com.project.book_catalog.security.util;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import org.springframework.security.core.GrantedAuthority;
import com.project.book_catalog.security.model.JwtToken;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class JwtTokenFactory {

    private final Key key;

    public JwtToken createJwtToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Claims claims = Jwts.claims().subject(username)
                .add("role", authorities.stream().map(a -> a.getAuthority()).collect(Collectors.toList())).build();

        LocalDateTime currentTime = LocalDateTime.now();
        Date currentDate = Date.from(currentTime.atZone(ZoneId.of("Asia/Jakarta")).toInstant());
        LocalDateTime expired = currentTime.plusMinutes(15);
        Date expiredDate = Date.from(expired.atZone(ZoneId.of("Asia/Jakarta")).toInstant());

        String token = Jwts.builder()
                .claims(claims)
                .issuer("project-spring")
                .issuedAt(currentDate)
                .expiration(expiredDate)
                .signWith(key)
                .compact();

        return new JwtToken(token, claims);
    }
}
