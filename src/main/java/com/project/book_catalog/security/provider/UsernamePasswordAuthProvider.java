package com.project.book_catalog.security.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.project.book_catalog.service.AppUserService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class UsernamePasswordAuthProvider implements AuthenticationProvider {

    private final AppUserService userService;

    private final PasswordEncoder encoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();
        UserDetails user = userService.loadUserByUsername(username);
        if(!encoder.matches(password, user.getPassword())) throw new BadCredentialsException("invalid password");
        return new UsernamePasswordAuthenticationToken(user, null,user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
