package com.project.book_catalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.book_catalog.security.filter.JwtAuthProcessingFilter;
import com.project.book_catalog.security.filter.UsernamePasswordAuthProcessingFilter;
import com.project.book_catalog.security.handler.UsernamePasswordFailureHandler;
import com.project.book_catalog.security.handler.UsernamePasswordSuccessHandler;
import com.project.book_catalog.security.provider.JwtAuthProvider;
import com.project.book_catalog.security.provider.UsernamePasswordAuthProvider;
import com.project.book_catalog.security.util.JwtTokenFactory;
import com.project.book_catalog.security.util.SkipPathRequestMathcer;
import com.project.book_catalog.util.TokenExtractor;

import java.util.List;

@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig {

    private final static String LOGIN_URL = "/login";
    private final static String BOOK_URL = "/books/**";
    private final static String AUTHOR_URL = "/author/**";
    private final static String USER_URL = "/users/**";

    private final static List<String> AUTH_ENDPOINTS = List.of( BOOK_URL, AUTHOR_URL);
    private final static List<String> PERMIT_ENDPOINTS = List.of(LOGIN_URL,USER_URL);

    @Autowired
    private UsernamePasswordAuthProvider usernamePasswordAuthProvider;

    @Autowired
    private JwtAuthProvider jwtAuthProvider;

    @Autowired
    public void registerProvider(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(usernamePasswordAuthProvider)
                .authenticationProvider(jwtAuthProvider);
    }

    @Bean
    public JwtAuthProcessingFilter jwtAuthProcessingFilter(TokenExtractor tokenExtractor,
            AuthenticationFailureHandler failureHandler,
            AuthenticationManager authenticationManager) {
        SkipPathRequestMathcer mathcer = new SkipPathRequestMathcer(PERMIT_ENDPOINTS, AUTH_ENDPOINTS);
        JwtAuthProcessingFilter filter = new JwtAuthProcessingFilter(mathcer, tokenExtractor, failureHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler(ObjectMapper objectMapper,
            JwtTokenFactory jwtTokenFactory) {
        return new UsernamePasswordSuccessHandler(objectMapper, jwtTokenFactory);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler(ObjectMapper objectMapper) {
        return new UsernamePasswordFailureHandler(objectMapper);
    }

    @Bean
    public UsernamePasswordAuthProcessingFilter authProcessingFilter(ObjectMapper objectMapper,
            AuthenticationSuccessHandler success,
            AuthenticationFailureHandler failure,
            AuthenticationManager authenticationManager) {
        UsernamePasswordAuthProcessingFilter filter = new UsernamePasswordAuthProcessingFilter(LOGIN_URL, objectMapper,
                success, failure);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

    @Bean
    public SecurityFilterChain filter(HttpSecurity http,
            UsernamePasswordAuthProcessingFilter usernamePasswordAuthProcessingFilter,
            JwtAuthProcessingFilter jwtAuthProcessingFilter) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers(BOOK_URL).authenticated())
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        http.addFilterBefore(usernamePasswordAuthProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthProcessingFilter, UsernamePasswordAuthProcessingFilter.class);
        return http.build();
    }
}
