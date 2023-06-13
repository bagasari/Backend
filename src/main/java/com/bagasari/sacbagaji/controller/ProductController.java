package com.bagasari.sacbagaji.controller;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.model.dto.req.ProductLikeDTO;
import com.bagasari.sacbagaji.model.dto.req.SearchFoodInDynamicRangeRequestDTO;
import com.bagasari.sacbagaji.model.dto.req.SearchFoodInStaticRangeRequestDTO;
import com.bagasari.sacbagaji.model.dto.res.AutoSearchWordListResponseDTO;
import com.bagasari.sacbagaji.model.dto.res.SearchFoodInDynamicRangeResponseDTO;
import com.bagasari.sacbagaji.model.dto.res.SearchFoodInStaticRangeResponseDTO;
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
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/product")
public class ProductController {

    private final ProductService productService;

    private final int PAGESIZE = 20;

    /**
     * @param keyword: 검색 키워드
     * @param location: 검색 국가/도시
     * @param sort: 정렬 조건 (id: 최신순, like: 좋아요순)
     * @param lastId: 마지막으로 조회한 product id. lastId+1부터 조회됨. 처음에는 null을 넘겨줌
     */
    @GetMapping("/search")
    public ResponseEntity<Slice<ProductDTO>> searchProduct(
            @RequestParam(required = false) String keyword,
            @RequestParam String location,
            @RequestParam String sort,
            @RequestParam(required = false) Long lastId
    ) {
        Pageable pageable = PageRequest.of(0, PAGESIZE, Sort.by(sort).descending());
        return ResponseEntity.ok(productService.searchProduct(keyword, location, pageable, lastId));
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

    @GetMapping("/search/auto")
    public ResponseEntity<AutoSearchWordListResponseDTO> selectAutoSearchWordList(@RequestParam(name = "word") String word) {
        return ResponseEntity.ok(productService.selectAutoSearchWordList(word));
    }

    /**
     * 특정 품목 기준으로 범위 내의 food 반환
     */
    @PostMapping("/map/food/dynamic")
    public ResponseEntity<List<List<SearchFoodInDynamicRangeResponseDTO>>> searchFoodInDynamicRange(@RequestBody SearchFoodInDynamicRangeRequestDTO dto) {
        return ResponseEntity.ok(productService.searchFoodInDynamicRange(dto));
    }

    /**
     * 특정 도시 중심범위 내의 food 반환
     */
    @PostMapping("/map/food/static")
    public ResponseEntity<List<List<SearchFoodInStaticRangeResponseDTO>>> searchFoodInStaticRange(@RequestBody SearchFoodInStaticRangeRequestDTO dto) {
        return ResponseEntity.ok(productService. searchFoodInStaticRange(dto));
    }
}
