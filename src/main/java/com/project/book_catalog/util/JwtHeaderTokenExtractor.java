package com.project.book_catalog.util;

import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

import io.micrometer.common.util.StringUtils;

@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {

    private static final String prefix = "Bearer ";

    @Override
    public String extract(String payload) {
        if (StringUtils.isBlank(payload))
            throw new AuthenticationServiceException("authorization header should be provided");
        if (payload.length() < prefix.length())
            throw new AuthenticationServiceException("invalid authorization header");
        return payload.substring(prefix.length(), payload.length());
    }

}
