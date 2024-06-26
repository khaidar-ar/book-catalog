package com.project.book_catalog;

import com.github.javafaker.Faker;
import com.project.book_catalog.domain.Author;
import com.project.book_catalog.domain.Publisher;
import com.project.book_catalog.dto.request.PublisherRequestDTO;
import com.project.book_catalog.repository.AuthorRepository;
import com.project.book_catalog.repository.PublisherRepository;

import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Slf4j
public class DataSeeder {

    @Autowired
    AuthorRepository authorRepository;

    @Autowired
    PublisherRepository publisherRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @Disabled
    void seedAuthor() {
        List<Author> authors = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 25; i++) {
            Author author = Author.builder()
                    .name(faker.name().fullName())
                    .birthDate(LocalDate.ofInstant
                            (faker.date().birthday().toInstant(), ZoneId.systemDefault())
                    )
                    .build();
            authors.add(author);
        }
        authorRepository.saveAll(authors);
    }

    @Test
    @Disabled
    void seedPublisher() {
        List<Publisher> publishers = new ArrayList<>();
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            PublisherRequestDTO publisherRequestDTO = PublisherRequestDTO.builder()
                    .name(faker.book().publisher())
                    .companyName(faker.company().name())
                    .address(faker.address().cityName())
                    .build();
            Publisher publisher = modelMapper.map(publisherRequestDTO, Publisher.class);
            publishers.add(publisher);
        }
        publisherRepository.saveAll(publishers);
    }

}
