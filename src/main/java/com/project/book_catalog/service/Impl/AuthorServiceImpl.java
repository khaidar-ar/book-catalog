package com.project.book_catalog.service.Impl;

import com.project.book_catalog.domain.Author;
import com.project.book_catalog.dto.request.AuthorRequestDTO;
import com.project.book_catalog.dto.response.AuthorResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;
import com.project.book_catalog.exception.BadRequestException;
import com.project.book_catalog.exception.ResourceNotFound;
import com.project.book_catalog.repository.AuthorRepository;
import com.project.book_catalog.service.AuthorService;
import com.project.book_catalog.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public AuthorResponseDTO findBySecureId(String id) {
        Author author = authorRepository.findBySecureId(id).orElseThrow(() -> new ResourceNotFound("Invalid id!!!"));
        AuthorResponseDTO authorResponse = modelMapper.map(author, AuthorResponseDTO.class);
        return authorResponse;
    }

    @Override
    public ResponsePageDTO<AuthorResponseDTO> findAll(Integer pages,
                                                      Integer limit,
                                                      String sortBy,
                                                      String direction,
                                                      String name) {
        String filter = StringUtils.isBlank(name) ? "%" : name + "%";
        Sort sort = Sort.by(PaginationUtil.sortBy(direction), sortBy);
        Pageable pageable = PageRequest.of(pages, limit, sort);
        Page<Author> authors = authorRepository.findByNameLikeIgnoreCase(filter, pageable);
        List<AuthorResponseDTO> authorResponseDTOS = authors.stream()
                .map(author -> modelMapper.map(author,AuthorResponseDTO.class))
                .collect(Collectors.toList());
        return PaginationUtil.resultPage(authorResponseDTOS, (int) authors.getTotalElements(), authors.getTotalPages());
    }

    @Override
    public List<Author> findAuthors(List<String> id) {
        List<Author> authors = authorRepository.findBySecureIdIn(id);
        if (authors.isEmpty()) throw new BadRequestException("Authors is empty!!!");
        return authors;
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
    public void update(String id, AuthorRequestDTO authorRequestDTO) {
        Author result = authorRepository.findBySecureId(id).
                orElseThrow(() -> new BadRequestException("Author with id " + id + " not found!!!"));
        result.setName(authorRequestDTO.getName());
        result.setBirthDate(LocalDate.parse(authorRequestDTO.getBirthDate()));
        authorRepository.save(result);
    }

    @Override
    public AuthorResponseDTO delete(String id) {
        Author author = authorRepository.findBySecureId(id)
                .orElseThrow(() -> new BadRequestException
                        ("Author with id " + id + " not found!!!"));
        AuthorResponseDTO authorResponseDTO = AuthorResponseDTO.builder()
                .name(author.getName())
                .birthDate(author.getBirthDate()).build();
        authorRepository.delete(author);
        return authorResponseDTO;
    }

    @Override
    public AuthorResponseDTO softDelete(String id) {
        Author author = authorRepository.findBySecureId(id)
                .orElseThrow(() -> new BadRequestException
                        ("Author with id " + id + " not found!!!"));
        author.setDeleted(Boolean.TRUE);
        authorRepository.save(author);
        AuthorResponseDTO authorResponseDTO = modelMapper.map(author, AuthorResponseDTO.class);
        return authorResponseDTO;
    }

    @Override
    public List<AuthorResponseDTO> construct(List<Author> authors) {
        List<AuthorResponseDTO> authorResponseDTOS = authors.stream().map(
                author -> modelMapper.map(author, AuthorResponseDTO.class)
        ).collect(Collectors.toList());
        return authorResponseDTOS;
    }
}
