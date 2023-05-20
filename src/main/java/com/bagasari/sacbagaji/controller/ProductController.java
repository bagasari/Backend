package com.bagasari.sacbagaji.controller;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.model.dto.req.ProductLikeDTO;
import com.bagasari.sacbagaji.security.Auth;
import com.bagasari.sacbagaji.security.AuthInfo;
import com.bagasari.sacbagaji.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    /**
     * @param keyword: 검색 키워드
     * @param sort: 정렬 조건
     * @param size: 한번에 나오는 product 개수
     * @param lastId: 마지막으로 조회한 product id. lastId+1부터 조회됨. 처음에는 null을 넘겨줌
     */
    @GetMapping("/search")
    public ResponseEntity<Slice<ProductDTO>> searchProduct(
            @RequestParam String keyword,
            @RequestParam String sort,
            @RequestParam int size,
            @RequestParam(required = false) Long lastId
    ) {
        Pageable pageable = PageRequest.of(0, size, Sort.by(sort).descending());
        return ResponseEntity.ok(productService.searchProduct(keyword, pageable, lastId));
    }

    @PostMapping("/like")
    public ResponseEntity<String> likeProduct(@Auth AuthInfo authInfo, @Valid @RequestBody ProductLikeDTO dto) {
        productService.likeProduct(authInfo.getEmail(), dto.getProductId());
        return ResponseEntity.ok("product like success");
    }

    @PostMapping("/dislike")
    public ResponseEntity<String> dislikeProduct(@Auth AuthInfo authInfo, @Valid @RequestBody ProductLikeDTO dto) {
        productService.dislikeProduct(authInfo.getEmail(), dto.getProductId());
        return ResponseEntity.ok("product dislike success");
    }

}
