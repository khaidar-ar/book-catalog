package com.project.book_catalog.service.Impl;

import com.project.book_catalog.domain.Publisher;
import com.project.book_catalog.dto.request.PublisherRequestDTO;
import com.project.book_catalog.dto.request.PublisherUpdateRequestDTO;
import com.project.book_catalog.dto.response.PublisherResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;
import com.project.book_catalog.exception.BadRequestException;
import com.project.book_catalog.repository.PublisherRepository;
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

@Service
@AllArgsConstructor
public class PublisherServiceImpl implements PublisherService {

    private final PublisherRepository publisherRepository;

    private ModelMapper modelMapper;

    @Override
    public ResponsePageDTO<PublisherResponseDTO> findAll(Integer page,
                                                         Integer limit,
                                                         String sortBy,
                                                         String direction,
                                                         String filter) {
        filter = StringUtils.isBlank(filter) ? "%" : filter + "%";
        Sort sort = Sort.by(PaginationUtil.sortBy(direction), sortBy);
        Pageable pageable = PageRequest.of(page, limit, sort);
        Page<Publisher> pageResult = publisherRepository.findByNameLikeIgnoreCase(filter, pageable);
        List<PublisherResponseDTO> publisherResponseDTOS = pageResult.stream()
                .map(publisher -> modelMapper.map(publisher,PublisherResponseDTO.class)
        ).collect(Collectors.toList());
        return PaginationUtil.resultPage(publisherResponseDTOS, (int) pageResult.getTotalElements(),
                pageResult.getTotalPages());
    }

    @Override
    public PublisherResponseDTO findById(String id) {
        Publisher publisher = publisherRepository
                .findBySecureId(id).orElseThrow(() ->
                        new BadRequestException("Publisher with id : " + id + " not found!!!"));
        PublisherResponseDTO publisherResponseDTO = modelMapper.map(publisher, PublisherResponseDTO.class);
        return publisherResponseDTO;
    }

    @Override
    public void create(PublisherRequestDTO publisherRequestDTO) {
        publisherRepository.save(modelMapper.map(publisherRequestDTO, Publisher.class));
    }

    @Override
    public PublisherResponseDTO update(String id, PublisherUpdateRequestDTO publisherUpdateRequestDTO) {
        Publisher publisher = publisherRepository.findBySecureId(id)
                .orElseThrow(() ->
                        new BadRequestException("Publisher with id : " + id + " not found!!!"));
        publisher.setName(publisherUpdateRequestDTO.getName() == null || publisherUpdateRequestDTO.getName().isBlank() ?
                publisher.getName() : publisherUpdateRequestDTO.getName());
        publisher.setCompanyName(publisherUpdateRequestDTO.getCompanyName() == null || publisherUpdateRequestDTO.getCompanyName().isBlank() ?
                publisher.getCompanyName() : publisherUpdateRequestDTO.getCompanyName());
        publisher.setAddress(publisherUpdateRequestDTO.getAddress());
        publisherRepository.save(publisher);
        PublisherResponseDTO publisherResponseDTO = modelMapper.map(publisherUpdateRequestDTO, PublisherResponseDTO.class);
        return publisherResponseDTO;
    }

    @Override
    public List<Publisher> findPublishers(List<String> id) {
        List<Publisher> publishers = publisherRepository.findBySecureIdIn(id);
        if (publishers.isEmpty()) throw new BadRequestException("Publishers is empty!!!");
        return publishers;
    }

}
