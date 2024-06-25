package com.project.book_catalog.security.filter;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.util.matcher.RequestMatcher;

import com.project.book_catalog.security.model.AnonymousAuth;
import com.project.book_catalog.security.model.JwtAuthToken;
import com.project.book_catalog.security.model.RawAccessJwtToken;
import com.project.book_catalog.util.TokenExtractor;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthProcessingFilter extends AbstractAuthenticationProcessingFilter {

    private TokenExtractor tokenExtractor;

    private AuthenticationFailureHandler failureHandler;

    public JwtAuthProcessingFilter(RequestMatcher requiresAuthenticationRequestMatcher,
            TokenExtractor tokenExtractor,AuthenticationFailureHandler failureHandler) {
        super(requiresAuthenticationRequestMatcher);
        this.tokenExtractor = tokenExtractor;
        this.failureHandler = failureHandler;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = tokenExtractor.extract(authorizationHeader);
        RawAccessJwtToken token = new RawAccessJwtToken(jwt);
        return this.getAuthenticationManager().authenticate(new JwtAuthToken(token));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        SecurityContext ctx = SecurityContextHolder.getContext();
        ctx.setAuthentication(authResult);
        SecurityContextHolder.setContext(ctx);
        super.successfulAuthentication(request, response, chain, authResult);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
        SecurityContextHolder.clearContext();
        SecurityContextHolder.getContext().setAuthentication(new AnonymousAuth());
        this.failureHandler.onAuthenticationFailure(request, response, failed);
    }

}
