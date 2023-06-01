package com.bagasari.sacbagaji.service;

import com.bagasari.sacbagaji.exception.CustomException;
import com.bagasari.sacbagaji.exception.ErrorCode;
import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.model.dto.res.AutoSearchWordListResponseDTO;
import com.bagasari.sacbagaji.model.entity.Product;
import com.bagasari.sacbagaji.model.entity.ProductLike;
import com.bagasari.sacbagaji.model.entity.User;
import com.bagasari.sacbagaji.repository.ProductLikeRepository;
import com.bagasari.sacbagaji.repository.ProductRepository;
import com.bagasari.sacbagaji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductLikeRepository productLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public Slice<ProductDTO> searchProduct(String keyword, Pageable pageable, Long lastId) {
        return productRepository.findAllByNameOrderBySort(keyword, pageable, lastId);
    }

    @Transactional
    public void likeProduct(String email, Long productId) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.BAD_CREDENTIAL_NONEXISTENT_ID));
        Product product = productRepository.findById(productId).orElseThrow(()->new CustomException(ErrorCode.NONEXISTENT_PRODUCT_ID));
        if(productLikeRepository.existsByUserAndProduct(user, product)) {
            throw new CustomException(ErrorCode.PRODUCT_LIKE_EXISTS);
        }

        productLikeRepository.save(
                new ProductLike(
                        null,
                        user,
                        product
                )
        );

        product.updateLike(1L);
    }

    @Transactional
    public void dislikeProduct(String email, Long productId) {
        User user = userRepository.findByEmail(email).orElseThrow(()->new CustomException(ErrorCode.BAD_CREDENTIAL_NONEXISTENT_ID));
        Product product = productRepository.findById(productId).orElseThrow(()->new CustomException(ErrorCode.NONEXISTENT_PRODUCT_ID));

        productLikeRepository.deleteByUserAndProduct(user, product);

        product.updateLike(-1L);
    }

    @Transactional
    public AutoSearchWordListResponseDTO selectAutoSearchWordList(String word) {

        // 빈 문자열인 경우
        if (word.isEmpty()) {
            return new AutoSearchWordListResponseDTO();
        }

        List<String> names = productRepository.findNameListOrderByProductNameAsc(word);
        // 일치하는 값이 없는 경우
        if (names.size() == 0) {
            return new AutoSearchWordListResponseDTO();
        } else {
            return new AutoSearchWordListResponseDTO(names);
        }
    }
}