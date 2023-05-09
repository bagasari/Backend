package com.bagasari.sacbagaji.service;

import com.bagasari.sacbagaji.exception.CustomException;
import com.bagasari.sacbagaji.exception.ErrorCode;
import com.bagasari.sacbagaji.model.dto.ProductDTO;
import com.bagasari.sacbagaji.model.dto.ProductListWithPurchaseDateDTO;
import com.bagasari.sacbagaji.model.dto.req.AccountRequestDTO;
import com.bagasari.sacbagaji.model.dto.req.FoodRequestDTO;
import com.bagasari.sacbagaji.model.dto.req.TransportationRequestDTO;
import com.bagasari.sacbagaji.model.dto.res.AccountResponseDTO;
import com.bagasari.sacbagaji.model.dto.res.AccountProductListResponseDTO;
import com.bagasari.sacbagaji.model.entity.*;
import com.bagasari.sacbagaji.repository.AccountBookRepository;
import com.bagasari.sacbagaji.repository.DestinationRepository;
import com.bagasari.sacbagaji.repository.ProductRepository;
import com.bagasari.sacbagaji.repository.UserRepository;
import com.bagasari.sacbagaji.security.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;
    private final ProductRepository productRepository;

    /**
     * 가계부 생성 서비스 로직
     */
    public void create(AuthInfo authInfo, AccountRequestDTO accountRequestDTO) {

        User user = userRepository.findByEmail(authInfo.getEmail()).get();

        // 가계부 생성
        AccountBook accountBook = AccountBook.builder()
                .user(user)
                .name(accountRequestDTO.getName())
                .startDate(accountRequestDTO.getStartDate())
                .endDate(accountRequestDTO.getEndDate())
                .isPrivate(accountRequestDTO.getIsPrivate())
                .totalPrice(0)
                .build();

        accountBookRepository.save(accountBook);

        // 여행지 저장
        List<Destination> destinationList = accountRequestDTO.getCityList()
                .stream()
                .map(city -> Destination.builder()
                        .accountBook(accountBook)
                        .city(new City(city))
                        .build())
                .collect(Collectors.toList());

        destinationRepository.saveAll(destinationList);
    }

    /**
     * 가계부 리스트 서비스 로직
     */
    public List<AccountResponseDTO> findList(AuthInfo authInfo) {

        User user = userRepository.findByEmail(authInfo.getEmail()).get();

        List<AccountBook> accountBooks = accountBookRepository.findAllByUserId(user.getId());

        List<AccountResponseDTO> list = accountBooks
                .stream()
                .sorted(Comparator.comparing(AccountBook::getCreateTime).reversed()) // 최신순으로 정렬
                .map(accountBook -> new AccountResponseDTO(accountBook))
                .collect(Collectors.toList());

        return list;
    }

    /**
     * 가계부 지출내역 생성 (먹거리)
     */
    public void createFoodProduct(AuthInfo authInfo, FoodRequestDTO foodRequestDTO) {

        User user = userRepository.findByEmail(authInfo.getEmail()).get();

        Optional<AccountBook> optionalAccountBook = accountBookRepository.findById(foodRequestDTO.getProduct().getAccountBookId());

        if (optionalAccountBook.isEmpty()) {
            throw new CustomException(ErrorCode.NONEXISTENT_ACCOUNT_ID);
        } else if (optionalAccountBook.get().getUser().getId() != user.getId()) {
            throw new CustomException(ErrorCode.INVALID_ACCESS_ACCOUNT);
        }

        AccountBook accountBook = optionalAccountBook.get();

        Food food = new Food(foodRequestDTO.getProduct(), accountBook, foodRequestDTO.getFood());

        productRepository.save(food);

        accountBook.updateTotalPrice(accountBook, accountBook.getTotalPrice() + foodRequestDTO.getProduct().getPrice());

        accountBookRepository.save(accountBook);

    }

    public void createTransportationProduct(AuthInfo authInfo, TransportationRequestDTO transportationRequestDTO) {

        User user = userRepository.findByEmail(authInfo.getEmail()).get();

        Optional<AccountBook> optionalAccountBook = accountBookRepository.findById(transportationRequestDTO.getProduct().getAccountBookId());

        if (optionalAccountBook.isEmpty()) {
            throw new CustomException(ErrorCode.NONEXISTENT_ACCOUNT_ID);
        } else if (optionalAccountBook.get().getUser().getId() != user.getId()) {
            throw new CustomException(ErrorCode.INVALID_ACCESS_ACCOUNT);
        }

        AccountBook accountBook = optionalAccountBook.get();

        Transportation transportation = new Transportation(transportationRequestDTO.getProduct(), optionalAccountBook.get(), transportationRequestDTO.getTransportation());

        productRepository.save(transportation);

        accountBook.updateTotalPrice(accountBook, accountBook.getTotalPrice() + transportationRequestDTO.getProduct().getPrice());

        accountBookRepository.save(accountBook);
    }

    public AccountProductListResponseDTO findCurAccountProductList(AuthInfo authInfo) {

        User user = userRepository.findByEmail(authInfo.getEmail()).get();

        AccountBook accountBook = accountBookRepository.findFirstByOrderByUpdateTimeDesc(user.getEmail());

        List<Product> products = productRepository.findAllByAccountBookId(accountBook.getId());

        List<ProductListWithPurchaseDateDTO> productListWithPurchaseDateDTOS = new ArrayList<>();

        Map<LocalDate, List<ProductDTO>> productsByPurchaseDate = new TreeMap<>();

        // purchaseDate별로 product 분류
        for (Product product : products) {
            productsByPurchaseDate.computeIfAbsent(product.getPurchaseDate(), k -> new ArrayList<>())
                    .add(new ProductDTO(product.getName(), product.getPrice()));
        }

        for (Map.Entry<LocalDate, List<ProductDTO>> entry : productsByPurchaseDate.entrySet()) {
            productListWithPurchaseDateDTOS.add(new ProductListWithPurchaseDateDTO(entry.getKey(), entry.getValue()));
        }

        return new AccountProductListResponseDTO(accountBook, productListWithPurchaseDateDTOS);

    }

    public AccountProductListResponseDTO findAccountProductList(AuthInfo authInfo, Long id) {

        User user = userRepository.findByEmail(authInfo.getEmail()).get();

        Optional<AccountBook> accountBookOptional = accountBookRepository.findById(id);

        if (accountBookOptional.isEmpty()) {
            throw new CustomException(ErrorCode.NONEXISTENT_ACCOUNT_ID);
        } else if (user.getId() != accountBookOptional.get().getUser().getId()) {
            throw new CustomException(ErrorCode.INVALID_ACCESS_ACCOUNT);
        }

        AccountBook accountBook = accountBookOptional.get();

        List<Product> products = productRepository.findAllByAccountBookId(id);

        List<ProductListWithPurchaseDateDTO> productListWithPurchaseDateDTOS = new ArrayList<>();

        Map<LocalDate, List<ProductDTO>> productsByPurchaseDate = new TreeMap<>();

        // purchaseDate별로 product 분류
        for (Product product : products) {
            productsByPurchaseDate.computeIfAbsent(product.getPurchaseDate(), k -> new ArrayList<>())
                    .add(new ProductDTO(product.getName(), product.getPrice(), product.getCity(), getProductType(product)));
        }

        for (Map.Entry<LocalDate, List<ProductDTO>> entry : productsByPurchaseDate.entrySet()) {
            productListWithPurchaseDateDTOS.add(new ProductListWithPurchaseDateDTO(entry.getKey(), entry.getValue()));
        }

        return new AccountProductListResponseDTO(accountBook, productListWithPurchaseDateDTOS);
    }

    private String getProductType(Product product) {
        if (product instanceof Food) {
            return "Food";
        } else if (product instanceof Transportation) {
            return "Transportation";
        } else {
            throw new CustomException(ErrorCode.INVALID_PRODUCT_TYPE);
        }
    }
}


