package com.bagasari.sacbagaji.controller;

import com.bagasari.sacbagaji.model.dto.req.AccountRequestDto;
import com.bagasari.sacbagaji.security.Auth;
import com.bagasari.sacbagaji.security.AuthInfo;
import com.bagasari.sacbagaji.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/account")
public class AccountBookController {

    private final AccountBookService accountBookService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@Auth AuthInfo authInfo, @RequestBody AccountRequestDto accountRequestDto) {
        accountBookService.create(authInfo, accountRequestDto);
        return ResponseEntity.ok("AccountBook create!!");
    }
}
