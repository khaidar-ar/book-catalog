package com.project.book_catalog.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.project.book_catalog.domain.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    Optional<AppUser> findByUsername(String username);

}
