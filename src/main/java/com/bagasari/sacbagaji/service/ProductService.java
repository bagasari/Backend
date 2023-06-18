package com.bagasari.sacbagaji.service;

import com.bagasari.sacbagaji.exception.CustomException;
import com.bagasari.sacbagaji.exception.ErrorCode;
import com.bagasari.sacbagaji.model.dto.FoodDTO;
import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.model.dto.req.SearchFoodInDynamicRangeRequestDTO;
import com.bagasari.sacbagaji.model.dto.req.SearchFoodInStaticRangeRequestDTO;
import com.bagasari.sacbagaji.model.dto.res.AutoSearchWordListResponseDTO;
import com.bagasari.sacbagaji.model.dto.res.SearchFoodInDynamicRangeResponseDTO;
import com.bagasari.sacbagaji.model.dto.res.SearchFoodInStaticRangeResponseDTO;
import com.bagasari.sacbagaji.model.dto.res.SearchProductResponseDto;
import com.bagasari.sacbagaji.model.entity.*;
import com.bagasari.sacbagaji.repository.FoodRepository;
import com.bagasari.sacbagaji.repository.ProductLikeRepository;
import com.bagasari.sacbagaji.repository.ProductRepository;
import com.bagasari.sacbagaji.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductLikeRepository productLikeRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public Slice<SearchProductResponseDto> searchProduct(String email, String keyword, String location, Pageable pageable, Long lastId) {
        return productRepository.findAllByNameAndLocationOrderBySort(
                userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.BAD_CREDENTIAL_NONEXISTENT_ID)), keyword, location, pageable, lastId
        );
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

    /**
     * 특정 품목 기준으로 범위 내의 food 반환
     */
    @Transactional
    public List<List<SearchFoodInDynamicRangeResponseDTO>> searchFoodInDynamicRange(String email, SearchFoodInDynamicRangeRequestDTO dto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.BAD_CREDENTIAL_NONEXISTENT_ID));
        Food targetFood = foodRepository.findById(dto.getId()).orElseThrow(()-> new CustomException(ErrorCode.NONEXISTENT_PRODUCT_ID));
        List<Food> foodList = foodRepository.findAllByName(dto.getName());
        List<List<SearchFoodInDynamicRangeResponseDTO>> foodGrid = new ArrayList<>();

        for(int i=0; i<16; i++) {
            foodGrid.add(new ArrayList<>());
        }

        double lat = Double.parseDouble(targetFood.getLatitude());
        double lon = Double.parseDouble(targetFood.getLongitude());

        for (Food food : foodList) {
            Product product = productRepository.findById(food.getId()).orElseThrow(()-> new CustomException(ErrorCode.NONEXISTENT_PRODUCT_ID));
            double foodLat = Double.parseDouble(food.getLatitude());
            double foodLon = Double.parseDouble(food.getLongitude());
            int sector = getSector(lat, lon, foodLat, foodLon);
            if(sector==-1) continue;
            foodGrid.get(sector).add(new SearchFoodInDynamicRangeResponseDTO(
                new ProductDTO(
                    food.getId(),
                    food.getAccountBook().getId(),
                    food.getName(),
                    food.getPrice(),
                    food.getPurchaseDate(),
                    food.getDetail(),
                    food.getCountry(),
                    food.getCity(),
                    food.getLikeCount(),
                        "Food"
            ), new FoodDTO(
                    food.getCount(),
                    food.getWeight(),
                    food.getLatitude(),
                    food.getLongitude()
            ), productLikeRepository.existsByUserAndProduct(user, product)));
        }

        return foodGrid;
    }

    /**
     * 특정 도시 중심범위 내의 food 반환
     * dto.name == null인 경우 전체 검색
     */
    @Transactional
    public List<List<SearchFoodInStaticRangeResponseDTO>> searchFoodInStaticRange(String email, SearchFoodInStaticRangeRequestDTO dto) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new CustomException(ErrorCode.BAD_CREDENTIAL_NONEXISTENT_ID));
        List<Food> foodList = foodRepository.findAllByNameContains(dto.getName());
        List<List<SearchFoodInStaticRangeResponseDTO>> foodGrid = new ArrayList<>();

        for(int i=0; i<16; i++) {
            foodGrid.add(new ArrayList<>());
        }

        double lat = Double.parseDouble(dto.getLatitude());
        double lon = Double.parseDouble(dto.getLongitude());

        for (Food food : foodList) {
            Product product = productRepository.findById(food.getId()).orElseThrow(()-> new CustomException(ErrorCode.NONEXISTENT_PRODUCT_ID));
            double foodLat = Double.parseDouble(food.getLatitude());
            double foodLon = Double.parseDouble(food.getLongitude());
            int sector = getSector(lat, lon, foodLat, foodLon);
            if(sector==-1) continue;
            foodGrid.get(sector).add(new SearchFoodInStaticRangeResponseDTO(
                    new ProductDTO(
                            food.getId(),
                            food.getAccountBook().getId(),
                            food.getName(),
                            food.getPrice(),
                            food.getPurchaseDate(),
                            food.getDetail(),
                            food.getCountry(),
                            food.getCity(),
                            food.getLikeCount(),
                            "Food"
                    ), new FoodDTO(
                    food.getCount(),
                    food.getWeight(),
                    food.getLatitude(),
                    food.getLongitude()
            ), productLikeRepository.existsByUserAndProduct(user, product)));
        }

        return foodGrid;
    }

    private int getSector(double centerLat, double centerLon, double lat, double lon) {
        double rLat = lat - centerLat;
        double rLon = lon - centerLon;

        if(-0.009<=rLat && rLat<=-0.0045) {
            int spare = getPlus(rLon);
            if(spare>=0) return 12+spare; else return -1;
        }
        else if(-0.0045<=rLat && rLat<=0) {
            int spare = getPlus(rLon);
            if(spare>=0) return 8+spare; else return -1;
        }
        else if(0<=rLat && rLat<=0.0045) {
            int spare = getPlus(rLon);
            if(spare>=0) return 4+spare; else return -1;
        }
        else if(0.0045<=rLat && rLat<=0.009) {
            int spare = getPlus(rLon);
            if(spare>=0) return spare; else return -1;
        }

        return -1;
    }

    private int getPlus(double lon) {
        if(-0.011<=lon && lon<=-0.0055) {
            return 0;
        }
        else if(-0.0055<=lon && lon<=0) {
            return 1;
        }
        else if(0<=lon && lon<=0.0055) {
            return 2;
        }
        else if(0.0055<=lon && lon<=0.011) {
            return 3;
        }

        return -1;
    }
}