package com.project.book_catalog.security.model;

import java.util.Collection;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtAuthToken extends AbstractAuthenticationToken {

    private RawAccessJwtToken rawAccessJwtToken;

    private UserDetails userDetails;

    public JwtAuthToken(RawAccessJwtToken rawAccessJwtToken) {
        super(null);
        this.rawAccessJwtToken = rawAccessJwtToken;
        super.setAuthenticated(false);
    }

    public JwtAuthToken(UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.eraseCredentials();
        this.userDetails = userDetails;
        this.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.rawAccessJwtToken;
    }

    @Override
    public Object getPrincipal() {
        return this.userDetails;
    }

    @Override
    public void eraseCredentials() {
        this.rawAccessJwtToken = null;
    }

}
