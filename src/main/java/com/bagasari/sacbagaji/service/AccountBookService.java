package com.bagasari.sacbagaji.service;

import com.bagasari.sacbagaji.model.dto.req.AccountRequestDto;
import com.bagasari.sacbagaji.model.entity.AccountBook;
import com.bagasari.sacbagaji.model.entity.User;
import com.bagasari.sacbagaji.repository.AccountBookRepository;
import com.bagasari.sacbagaji.repository.UserRepository;
import com.bagasari.sacbagaji.security.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountBookService {

    private final AccountBookRepository accountBookRepository;
    private final UserRepository userRepository;

    /**
     * 가계부 생성 서비스 로직
     */
    public void create(AuthInfo authInfo, AccountRequestDto accountRequestDto) {

        User user = userRepository.findByEmail(authInfo.getEmail()).get();

        AccountBook accountBook = AccountBook.builder()
                .user(user)
                .name(accountRequestDto.getName())
                .startDate(accountRequestDto.getStartDate())
                .endDate(accountRequestDto.getEndDate())
                .isPrivate(accountRequestDto.getIsPrivate())
                .totalPrice(0)
                .build();

        accountBookRepository.save(accountBook);
    }

}
