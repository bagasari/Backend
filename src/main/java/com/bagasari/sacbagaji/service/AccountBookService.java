package com.bagasari.sacbagaji.service;

import com.bagasari.sacbagaji.model.dto.req.AccountRequestDTO;
import com.bagasari.sacbagaji.model.dto.res.AccountResponseDTO;
import com.bagasari.sacbagaji.model.entity.AccountBook;
import com.bagasari.sacbagaji.model.entity.City;
import com.bagasari.sacbagaji.model.entity.Destination;
import com.bagasari.sacbagaji.model.entity.User;
import com.bagasari.sacbagaji.repository.AccountBookRepository;
import com.bagasari.sacbagaji.repository.DestinationRepository;
import com.bagasari.sacbagaji.repository.UserRepository;
import com.bagasari.sacbagaji.security.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final UserRepository userRepository;
    private final DestinationRepository destinationRepository;

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

        List<AccountBook> accountBooks = accountBookRepository.findAll();

        List<AccountResponseDTO> list = accountBooks
                .stream()
                .sorted(Comparator.comparing(AccountBook::getCreateTime).reversed()) // 최신순으로 정렬
                .map(accountBook -> new AccountResponseDTO(accountBook))
                .collect(Collectors.toList());

        return list;
    }
}
