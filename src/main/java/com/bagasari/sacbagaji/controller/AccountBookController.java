package com.bagasari.sacbagaji.controller;

import com.bagasari.sacbagaji.model.dto.req.AccountRequestDTO;
import com.bagasari.sacbagaji.model.dto.req.FoodRequestDTO;
import com.bagasari.sacbagaji.model.dto.req.TransportationRequestDTO;
import com.bagasari.sacbagaji.model.dto.res.AccountResponseDTO;
import com.bagasari.sacbagaji.model.dto.res.AccountProductListResponseDTO;
import com.bagasari.sacbagaji.security.Auth;
import com.bagasari.sacbagaji.security.AuthInfo;
import com.bagasari.sacbagaji.service.AccountBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/account")
public class AccountBookController {

    private final AccountBookService accountBookService;

    @PostMapping("/create")
    public ResponseEntity<String> create(@Auth AuthInfo authInfo, @RequestBody AccountRequestDTO accountRequestDto) {
        accountBookService.create(authInfo, accountRequestDto);
        return ResponseEntity.ok("AccountBook create!!");
    }

    @GetMapping("/list")
    public ResponseEntity<List<AccountResponseDTO>> findList(@Auth AuthInfo authInfo) {
        return ResponseEntity.ok(accountBookService.findList(authInfo));
    }

    @PostMapping("/product/food")
    public ResponseEntity<String> createFoodProduct(@Auth AuthInfo authInfo, @RequestBody FoodRequestDTO foodRequestDTO) {
        accountBookService.createFoodProduct(authInfo, foodRequestDTO);
        return ResponseEntity.ok("Food Product create!!");
    }

    @PostMapping("/product/transport")
    public ResponseEntity<String> createTransportationProduct(@Auth AuthInfo authInfo, @RequestBody TransportationRequestDTO transportationRequestDTO) {
        accountBookService.createTransportationProduct(authInfo, transportationRequestDTO);
        return ResponseEntity.ok("Transportation Product create!!");
    }

    @GetMapping("/current")
    public ResponseEntity<AccountProductListResponseDTO> findCurAccountProductList(@Auth AuthInfo authInfo) {
        return ResponseEntity.ok(accountBookService.findCurAccountProductList(authInfo));
    }

    @GetMapping("")
    public ResponseEntity<AccountProductListResponseDTO> findAccountProductList(@Auth AuthInfo authInfo, @RequestParam(name = "accountId") Long id) {
        return ResponseEntity.ok(accountBookService.findAccountProductList(authInfo, id));
    }

}
