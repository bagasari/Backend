package com.bagasari.sacbagaji.controller;

import com.bagasari.sacbagaji.security.Auth;
import com.bagasari.sacbagaji.security.AuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/test")
public class TestController {

    @GetMapping
    public String test() {
        return "test";
    }

    @GetMapping("/authentication")
    public String authenticationTest(
            @Auth AuthInfo authInfo
    ) {
        return "success";
    }
}
