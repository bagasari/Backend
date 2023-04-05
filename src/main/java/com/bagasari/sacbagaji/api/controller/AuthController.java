package com.bagasari.sacbagaji.api.controller;

import com.bagasari.sacbagaji.api.dto.LoginDTO;
import com.bagasari.sacbagaji.api.dto.TokenDTO;
import com.bagasari.sacbagaji.jwt.JwtFilter;
import com.bagasari.sacbagaji.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;


    @PostMapping("/authenticate")
    public ResponseEntity<TokenDTO> authorize(@Valid @RequestBody LoginDTO loginDto) {

        // AuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());

        // authenticate 실행될 때 CustomUserDetailsService 클래스의 loadUserByUsername 메소드 실행하여
        // Authentication 객체 생성
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // authentication 객체를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증 정보를 기반으로 jwt 토큰 생성
        String jwt = tokenProvider.createToken(authentication);

        // response header에 토큰을 넣어주고
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + jwt);

        // Response Body에도 넣어서 리턴
        return new ResponseEntity<>(new TokenDTO(jwt), httpHeaders, HttpStatus.OK);
    }
}
