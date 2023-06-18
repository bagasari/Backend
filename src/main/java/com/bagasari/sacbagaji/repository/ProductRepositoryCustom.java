package com.bagasari.sacbagaji.repository;

import com.bagasari.sacbagaji.model.dto.res.SearchProductResponseDto;
import com.bagasari.sacbagaji.model.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface ProductRepositoryCustom {

    Slice<SearchProductResponseDto> findAllByNameAndLocationOrderBySort(User user, String keyword, String location, Pageable pageable, Long lastId);

    List<String> findNameListOrderByProductNameAsc(String word);
}
