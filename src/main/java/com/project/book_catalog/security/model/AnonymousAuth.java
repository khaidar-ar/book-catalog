package com.project.book_catalog.security.model;
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class AnonymousAuth extends AbstractAuthenticationToken {

    public AnonymousAuth() {
        super(null);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public boolean isAuthenticated() {
        return false;
    }

}
