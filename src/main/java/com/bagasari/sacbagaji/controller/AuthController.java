package com.bagasari.sacbagaji.controller;

import com.bagasari.sacbagaji.model.dto.req.RefreshRequestDTO;
import com.bagasari.sacbagaji.model.dto.req.SignInRequestDTO;
import com.bagasari.sacbagaji.model.dto.TokenDTO;
import com.bagasari.sacbagaji.model.dto.req.SignUpRequestDTO;
import com.bagasari.sacbagaji.repository.AuthorityRepository;
import com.bagasari.sacbagaji.repository.UserRepository;
import com.bagasari.sacbagaji.security.jwt.JwtFilter;
import com.bagasari.sacbagaji.service.AuthService;
import lombok.RequiredArgsConstructor;
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

    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/signUp")
    public ResponseEntity<String> signup(@Valid @RequestBody SignUpRequestDTO dto) {
        authService.signUp(dto);
        return ResponseEntity.ok("sign up success!");
    }

    @PostMapping("/signIn")
    public ResponseEntity<TokenDTO> signIn(@Valid @RequestBody SignInRequestDTO dto, HttpServletResponse response) {
        TokenDTO tokenDTO = authService.signIn(dto);

//        response.setHeader(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDTO.getToken());

        return ResponseEntity.ok(tokenDTO);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenDTO> refresh(@Valid @RequestBody RefreshRequestDTO dto) {
        TokenDTO token = authService.refresh(dto);



        return ResponseEntity.ok(token);
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
