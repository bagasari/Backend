package com.bagasari.sacbagaji.controller;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.model.dto.res.ProductListResponseDTO;
import com.bagasari.sacbagaji.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    /**
     * @param keyword: 검색 키워드
     * @param sort: 정렬 조건
     * @param size: 한번에 나오는 product 개수
     * @param page: page number
     */
    @GetMapping("/search")
    public ResponseEntity<> searchProduct(
            @RequestParam String keyword,
            @RequestParam String sort,
            @RequestParam int size,
            @RequestParam int page
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(productService.searchProduct(sort, keyword, pageable));
    }

}
