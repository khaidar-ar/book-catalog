package com.project.book_catalog.security.provider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import com.project.book_catalog.security.model.JwtAuthToken;
import com.project.book_catalog.security.model.RawAccessJwtToken;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtAuthProvider implements AuthenticationProvider {

    private  SecretKey key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        RawAccessJwtToken token = (RawAccessJwtToken) authentication.getCredentials();
        Jws<Claims> claims = token.parseClaims(key);
        String subject = claims.getPayload().getSubject();
        List<String> roles = claims.getPayload().get("role",List.class);
        List<GrantedAuthority> authorities = roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        UserDetails userDetails = new UserDetails() {

            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return authorities;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return subject;
            }
            
        };
        return new JwtAuthToken(userDetails,authorities);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthToken.class.isAssignableFrom(authentication);
    }

}
