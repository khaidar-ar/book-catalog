package com.project.book_catalog.service.Impl;

import com.project.book_catalog.domain.Author;
import com.project.book_catalog.dto.request.AuthorRequestDTO;
import com.project.book_catalog.dto.response.AuthorResponseDTO;
import com.project.book_catalog.exception.BadRequestException;
import com.project.book_catalog.repository.AuthorRepository;
import com.project.book_catalog.service.AuthorService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    private final ModelMapper modelMapper;

    @Override
    public AuthorResponseDTO findById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow(() -> new BadRequestException("Invalid id!!!"));
        AuthorResponseDTO authorResponse = modelMapper.map(author, AuthorResponseDTO.class);
        return authorResponse;
    }

    @Override
    public List<AuthorResponseDTO> findAll() {
        List<AuthorResponseDTO> authorResponseDTOList = authorRepository
                .findAll().stream().map(author ->
                        modelMapper.map(author, AuthorResponseDTO.class))
                .collect(Collectors.toList());
        return authorResponseDTOList;
    }

    @Override
    public void create(AuthorRequestDTO authorRequest) {
        Author author = Author.builder()
                .name(authorRequest.getName())
                .birthDate(LocalDate.parse(authorRequest.getBirthDate()))
                .build();
        authorRepository.save(author);
    }

    @Override
    public void createBatch(List<AuthorRequestDTO> authorRequestDTOList) {
        List<Author> authors = authorRequestDTOList.stream().map(a -> {
            Author author = Author.builder()
                    .name(a.getName())
                    .birthDate(LocalDate.parse(a.getBirthDate()))
                    .build();
            return author;
        }).collect(Collectors.toList());
        authorRepository.saveAll(authors);
    }

    @Override
    public void update(Long id, AuthorRequestDTO author) {
       Author result = authorRepository.findById(id).
                orElseThrow(()-> new BadRequestException("Author with id " + id + " not found!!!"));
        Author updateAuthor = Author.builder()
                .id(result.getId())
                .name(result.getName())
                .birthDate(result.getBirthDate()).build();
        authorRepository.save(updateAuthor);
    }

    @Override
    public AuthorResponseDTO delete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new BadRequestException
                        ("Author with id " + id + " not found!!!"));
        AuthorResponseDTO authorResponseDTO = AuthorResponseDTO.builder()
                .name(author.getName())
                .birthDate(author.getBirthDate()).build();
        authorRepository.delete(author);
        return authorResponseDTO;
    }

    @Override
    public AuthorResponseDTO softDelete(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(()-> new BadRequestException
                        ("Author with id " + id + " not found!!!"));
        author.setDeleted(Boolean.TRUE);
        authorRepository.save(author);
        AuthorResponseDTO authorResponseDTO = AuthorResponseDTO.builder()
                .name(author.getName())
                .birthDate(author.getBirthDate())
                .build();
        return authorResponseDTO;
    }
}
