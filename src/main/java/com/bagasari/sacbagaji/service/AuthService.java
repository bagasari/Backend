package com.bagasari.sacbagaji.service;

import com.bagasari.sacbagaji.exception.auth.UserExistsException;
import com.bagasari.sacbagaji.model.dto.req.SignInRequestDto;
import com.bagasari.sacbagaji.model.dto.req.SignUpRequestDto;
import com.bagasari.sacbagaji.model.dto.TokenDTO;
import com.bagasari.sacbagaji.model.entity.Authority;
import com.bagasari.sacbagaji.model.entity.User;
import com.bagasari.sacbagaji.repository.UserRepository;
import com.bagasari.sacbagaji.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 회원가입 서비스 로직
     */
    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {
        if (userRepository.findOneWithAuthoritiesByEmail(signUpRequestDto.getEmail()).orElse(null) != null) {
            throw new UserExistsException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .email(signUpRequestDto.getEmail())
                .password(passwordEncoder.encode(signUpRequestDto.getPassword()))
                .name(signUpRequestDto.getName())
                .authorities(Collections.singleton(authority))
                .build();

        userRepository.save(user);
    }

    @Transactional
    public TokenDTO signIn(SignInRequestDto dto) {
        // AuthenticationToken 객체 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        // Authentication 객체 생성
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // authentication 객체를 SecurityContext에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 인증 정보를 기반으로 jwt 토큰 생성하여 리턴
        return new TokenDTO(tokenProvider.generateToken(authentication));
    }
}