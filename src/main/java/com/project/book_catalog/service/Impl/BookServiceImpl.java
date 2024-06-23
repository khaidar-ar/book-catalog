package com.project.book_catalog.service.Impl;

import com.project.book_catalog.domain.Author;
import com.project.book_catalog.domain.Book;
import com.project.book_catalog.domain.Category;
import com.project.book_catalog.domain.Publisher;
import com.project.book_catalog.dto.request.BookRequestDTO;
import com.project.book_catalog.dto.response.BookDetailResponseDTO;
import com.project.book_catalog.dto.response.BookResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;
import com.project.book_catalog.exception.BadRequestException;
import com.project.book_catalog.repository.BookRepository;
import com.project.book_catalog.service.AuthorService;
import com.project.book_catalog.service.BookService;
import com.project.book_catalog.service.CategoryService;
import com.project.book_catalog.service.PublisherService;
import com.project.book_catalog.util.PaginationUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    private final AuthorService authorService;

    private final PublisherService publisherService;

    private final CategoryService categoryService;

    private ModelMapper modelMapper;

    @Override
    public void create(BookRequestDTO bookRequestDTO) {
        List<Author> authors = authorService.findAuthors(bookRequestDTO.getAuthors());
        List<Category> categories = categoryService.findCategories(bookRequestDTO.getCategories());
        Publisher publisher = publisherService.findPublisher(bookRequestDTO.getPublisherId());
        Book book = Book.builder()
                .title(bookRequestDTO.getTitle())
                .description(bookRequestDTO.getDescription())
                .authors(authors)
                .categories(categories)
                .publisher(publisher)
                .build();
        bookRepository.save(book);
    }

    @Override
    public ResponsePageDTO<BookResponseDTO> findBooks(Integer pages,
                                                      Integer limit,
                                                      String sortBy,
                                                      String direction,
                                                      String name
    ) {
        String filter = StringUtils.isBlank(name) ? "%" : name + "%";
        Sort sort = Sort.by(PaginationUtil.sortBy(direction), sortBy);
        Pageable pageable = PageRequest.of(pages, limit, sort);
        Page<Book> bookPage = bookRepository.findByTitleLikeIgnoreCase(filter, pageable);
        List<BookResponseDTO> bookResponseDTOS = bookPage.stream().map(
                book -> modelMapper.map(book, BookResponseDTO.class)
        ).collect(Collectors.toList());

        return PaginationUtil.resultPage(bookResponseDTOS, (int) bookPage.getTotalElements(), bookPage.getTotalPages());
    }

    @Override
    public BookDetailResponseDTO findBookDetail(String id) {
        Book book = bookRepository.findBySecureId(id)
                .orElseThrow(() -> new BadRequestException("Book with id : " + id + ",not found!!!"));
        BookDetailResponseDTO bookResponseDTO = BookDetailResponseDTO.builder()
                .title(book.getTitle())
                .description(book.getDescription())
                .publisherResponseDTO(publisherService.construct(book.getPublisher()))
                .categoryResponseDTOS(categoryService.construct(book.getCategories()))
                .authorResponseDTOS(authorService.construct(book.getAuthors()))
                .build();
        return bookResponseDTO;
    }
}
