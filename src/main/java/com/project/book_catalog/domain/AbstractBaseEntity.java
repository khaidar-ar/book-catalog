package com.project.book_catalog.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@MappedSuperclass
public abstract class AbstractBaseEntity {

    @Column(name = "secure_id",nullable = false,unique = true)
    private String secureId = UUID.randomUUID().toString();

    @Column(name = "deleted",columnDefinition = "boolean default false")
    private Boolean deleted;
}
