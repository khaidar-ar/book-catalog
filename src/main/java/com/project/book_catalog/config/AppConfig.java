package com.project.book_catalog.config;

import java.security.Key;

import javax.crypto.SecretKey;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.book_catalog.security.util.JwtTokenFactory;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Configuration
public class AppConfig {

    @Bean
    public Key key(){
        byte[] keys = Decoders.BASE64.decode("bookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkey");
        return Keys.hmacShaKeyFor(keys);
    }

    @Bean
    public SecretKey secretKey(){
        byte[] keys = Decoders.BASE64.decode("bookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkeybookcatalogkey");
        return Keys.hmacShaKeyFor(keys);
    }

    @Bean
    public PasswordEncoder encoder(){
        return new BCryptPasswordEncoder();
    } 

    @Bean
    public ObjectMapper objectMapper(){
        return new ObjectMapper();
    }

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
        .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }

    @Bean
    public JwtTokenFactory jwtTokenFactory(Key key){
        return new JwtTokenFactory(key);
    }

}
