package com.bagasari.sacbagaji.config.jwt;

import com.bagasari.sacbagaji.exception.ExceptionHandlerFilter;
import com.bagasari.sacbagaji.security.jwt.JwtFilter;
import com.bagasari.sacbagaji.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * TokenProvider, JwtFilter를 SecurityConfig에 적용할 때 사용할 JwtSecurityConfig 클래스
 */
@RequiredArgsConstructor
public class JwtSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final TokenProvider tokenProvider;

    @Override
    public void configure(HttpSecurity http) {
        // JwtFilter를 통해 Security 로직에 필터 등록
        http.addFilterBefore(
                new JwtFilter(tokenProvider),
                UsernamePasswordAuthenticationFilter.class
        );
        //JwtFilter 이전에 예외를 처리 가능한 필터 등록
        http.addFilterBefore(
                new ExceptionHandlerFilter(),
                JwtFilter.class
        );
    }
}