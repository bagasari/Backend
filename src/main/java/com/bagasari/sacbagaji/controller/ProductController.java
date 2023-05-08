//package com.bagasari.sacbagaji.controller;
//
//import com.bagasari.sacbagaji.model.dto.res.ProductListResponseDTO;
//import com.bagasari.sacbagaji.security.Auth;
//import com.bagasari.sacbagaji.security.AuthInfo;
//import com.bagasari.sacbagaji.service.ProductService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/v1/product")
//public class ProductController {
//
//    private final ProductService productService;
//
//    @GetMapping("/all")
//    public ResponseEntity<ProductListResponseDTO> getAllProductList(@Auth AuthInfo authInfo) {
//        return ResponseEntity.ok(new ProductListResponseDTO(productService.getAllProductList()));
//    }
//
//    @PostMapping("/search")
//    public ResponseEntity<ProductListResponseDTO> getProductList(@Auth AuthInfo authInfo, @RequestParam String keyword) {
//        return ResponseEntity.ok(new ProductListResponseDTO(productService.getProducts(keyword)));
//    }
//}
