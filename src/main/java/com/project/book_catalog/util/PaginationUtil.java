package com.project.book_catalog.util;

import com.project.book_catalog.dto.response.ResponsePageDTO;
import org.springframework.data.domain.Sort;

import java.util.List;

public class PaginationUtil {

    public static <T> ResponsePageDTO<T> resultPage(List<T> data, Integer elements, Integer pages) {
        ResponsePageDTO<T> responsePageDTO = new ResponsePageDTO<T>();
        responsePageDTO.setList(data);
        responsePageDTO.setElements(elements);
        responsePageDTO.setPages(pages);
        return responsePageDTO;
    }

    public static Sort.Direction sortBy(String direction) {
        if (direction.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else return Sort.Direction.DESC;
    }
}
