package com.project.book_catalog.service;

import com.project.book_catalog.domain.Publisher;
import com.project.book_catalog.dto.request.PublisherRequestDTO;
import com.project.book_catalog.dto.request.PublisherUpdateRequestDTO;
import com.project.book_catalog.dto.response.PublisherResponseDTO;
import com.project.book_catalog.dto.response.ResponsePageDTO;

import java.util.List;

public interface PublisherService {

    public ResponsePageDTO<PublisherResponseDTO> findAll(Integer page,
                                                               Integer limit,
                                                               String sortBy,
                                                               String direction,
                                                               String filter);

    public PublisherResponseDTO findById(String id);

    public void create(PublisherRequestDTO publisherRequestDTO);

    public PublisherResponseDTO update(String id, PublisherUpdateRequestDTO publisherUpdateRequestDTO);

    public List<Publisher> findPublishers(List<String> id);

    public Publisher findPublisher(String id);

    public PublisherResponseDTO construct(Publisher publisher);



}
