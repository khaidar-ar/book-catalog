package com.project.book_catalog.service.Impl;

import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.book_catalog.dto.response.UserDetailDTO;
import com.project.book_catalog.exception.ResourceNotFound;
import com.project.book_catalog.repository.AppUserRepository;
import com.project.book_catalog.service.AppUserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserRepository userRepository;

    private ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
        .orElseThrow(()->new ResourceNotFound("invalid username"));
    }

    @Override
    public UserDetailDTO findUserDetailDTO() {
        SecurityContext ctx = SecurityContextHolder.getContext();
        UserDetailDTO userDetailDTO = UserDetailDTO.builder()
        .username(ctx.getAuthentication().getName())
        .build();
        return userDetailDTO;
    }

}
