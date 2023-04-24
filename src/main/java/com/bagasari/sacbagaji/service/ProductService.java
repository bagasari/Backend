package com.bagasari.sacbagaji.service;

import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public List<ProductDTO> getAllProductList() {
        return productRepository.findAll()
                .stream().map(p -> new ProductDTO(
                        p.getAccountBook().getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getPurchaseDate(),
                        p.getDetail(),
                        p.getCountry(),
                        p.getCity()
                )).collect(Collectors.toList());
    }

    @Transactional
    public List<ProductDTO> getProducts(String keyword) {
        return productRepository.findByNameContaining(keyword)
                .stream().map(p-> new ProductDTO(
                        p.getAccountBook().getId(),
                        p.getName(),
                        p.getPrice(),
                        p.getPurchaseDate(),
                        p.getDetail(),
                        p.getCountry(),
                        p.getCity()
                )).collect(Collectors.toList());
    }
}
