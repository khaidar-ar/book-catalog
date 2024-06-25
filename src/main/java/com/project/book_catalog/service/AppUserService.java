package com.project.book_catalog.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.project.book_catalog.dto.response.UserDetailDTO;

public interface AppUserService extends UserDetailsService {

    UserDetailDTO findUserDetailDTO();
}
