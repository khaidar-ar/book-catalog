package com.project.book_catalog.service.Impl;

import com.project.book_catalog.domain.Category;
import com.project.book_catalog.dto.request.CategoryRequestDTO;
import com.project.book_catalog.dto.response.CategoryResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;
import com.project.book_catalog.exception.BadRequestException;
import com.project.book_catalog.repository.CategoryRepository;
import com.project.book_catalog.service.CategoryService;
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

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private ModelMapper modelMapper;

    @Override
    public void createAndUpdate(CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository.findByCode(
                categoryRequestDTO.getCode().toLowerCase()
        ).orElse(new Category());
        if (category.getCode() == null) {
            category.setCode(categoryRequestDTO.getCode().toLowerCase());
        }
        category.setName(categoryRequestDTO.getName());
        category.setDescription(categoryRequestDTO.getDescription());
        categoryRepository.save(category);
    }

    @Override
    public ResponsePageDTO<CategoryResponseDTO> findAll(Integer page,
                                                        Integer limit,
                                                        String sortBy,
                                                        String direction,
                                                        String name) {
        String filter = StringUtils.isBlank(name) ? "%" : name + "%";
        Sort sort = Sort.by(PaginationUtil.sortBy(direction), sortBy);
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Category> categories = categoryRepository.findByNameLikeIgnoreCase(filter, pageable);
        List<CategoryResponseDTO> results = categories.stream()
                .map(category ->
                        modelMapper.map(category, CategoryResponseDTO.class)
                ).collect(Collectors.toList());
        return PaginationUtil.resultPage(results, (int) categories.getTotalElements(), categories.getTotalPages());
    }

    @Override
    public List<Category> findCategories(List<String> codes) {
        List<Category> categories = categoryRepository.findByCodeIn(codes);
        if(categories.isEmpty()) throw new BadRequestException("Categories is empty");
        return categories;
    }

    @Override
    public List<CategoryResponseDTO> construct(List<Category> categories) {
        List<CategoryResponseDTO> categoryResponseDTOS = categories.stream().map(
                category -> modelMapper.map(category, CategoryResponseDTO.class)
        ).collect(Collectors.toList());
        return categoryResponseDTOS;
    }
}
