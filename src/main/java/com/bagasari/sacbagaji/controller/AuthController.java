package com.bagasari.sacbagaji.controller;

import com.bagasari.sacbagaji.model.dto.req.SignInRequestDto;
import com.bagasari.sacbagaji.model.dto.TokenDTO;
import com.bagasari.sacbagaji.model.dto.req.SignUpRequestDto;
import com.bagasari.sacbagaji.security.jwt.JwtFilter;
import com.bagasari.sacbagaji.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/auth")
public class AuthController {
    ;
    private final AuthService authService;

    @PostMapping("/signUp")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignUpRequestDto dto) {
        authService.signUp(dto);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenDTO> signIn(@Valid @RequestBody SignInRequestDto dto) {
        TokenDTO tokenDTO = authService.signIn(dto);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDTO.getToken());

        return new ResponseEntity<>(tokenDTO, httpHeaders, HttpStatus.OK);
    }

    @GetMapping("/hello")
    public ResponseEntity<String> hello() {
        return ResponseEntity.ok("hello");
    }

    @PostMapping("/test-redirect")
    public void testRedirect(HttpServletResponse response) throws IOException {
        response.sendRedirect("/api/user");
    }

    @GetMapping("/user")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public String testUser() {
        return "유저입니다.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public String testAdmin() {
        return "관리자입니다.";
    }

}
