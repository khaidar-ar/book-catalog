package com.project.book_catalog.security.handler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.book_catalog.security.model.JwtToken;
import com.project.book_catalog.security.util.JwtTokenFactory;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UsernamePasswordSuccessHandler implements AuthenticationSuccessHandler {

    private final ObjectMapper objectMapper;

    private final JwtTokenFactory jwtTokenFactory;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        JwtToken token = jwtTokenFactory.createJwtToken(userDetails.getUsername(),userDetails.getAuthorities());
        Map<String, String> result = new HashMap<>();
        result.put("token", token.getRaw());
        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        objectMapper.writeValue(response.getWriter(), result);
    }

}
