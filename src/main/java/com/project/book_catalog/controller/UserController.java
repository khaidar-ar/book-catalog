package com.project.book_catalog.controller;

import org.springframework.web.bind.annotation.RestController;

import com.project.book_catalog.dto.response.UserDetailDTO;
import com.project.book_catalog.repository.AppUserRepository;
import com.project.book_catalog.service.AppUserService;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@AllArgsConstructor
@RestController
public class UserController {

    private final AppUserService appUserService;

    @GetMapping("/users")
    public ResponseEntity<UserDetailDTO> findUser() {
        return ResponseEntity.ok().body(appUserService.findUserDetailDTO());
    }
    

}
