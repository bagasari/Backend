package com.bagasari.sacbagaji.service;

import com.bagasari.sacbagaji.exception.CustomException;
import com.bagasari.sacbagaji.exception.ErrorCode;
import com.bagasari.sacbagaji.model.dto.req.RefreshRequestDTO;
import com.bagasari.sacbagaji.model.dto.req.SignInRequestDTO;
import com.bagasari.sacbagaji.model.dto.req.SignUpRequestDTO;
import com.bagasari.sacbagaji.model.dto.TokenDTO;
import com.bagasari.sacbagaji.model.entity.Authority;
import com.bagasari.sacbagaji.model.entity.User;
import com.bagasari.sacbagaji.repository.UserRepository;
import com.bagasari.sacbagaji.security.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Optional;

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
    public void signUp(SignUpRequestDTO signUpRequestDto) {
        if (userRepository.findOneWithAuthoritiesByEmail(signUpRequestDto.getEmail()).orElse(null) != null) {
            throw new CustomException(ErrorCode.USER_EXISTS);
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
    public TokenDTO signIn(SignInRequestDTO dto) {
        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(()->new CustomException(ErrorCode.BAD_CREDENTIAL_NONEXISTENT_ID));
        if(!passwordEncoder.matches(dto.getPassword(), user.getPassword())) throw new CustomException(ErrorCode.BAD_CREDENTIAL_INVALID_PWD);

        // 인증 정보를 기반으로 jwt access, refresh토큰 생성하여 리턴
        String accessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getAuthorities());
        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail(), user.getAuthorities());

        user.renewRefreshToken(refreshToken);

        return new TokenDTO(accessToken, refreshToken);
    }

    @Transactional
    public TokenDTO refresh(RefreshRequestDTO dto) {
        String email = tokenProvider.getUserEmailFromToken(dto.getRefreshToken());

        User user = userRepository.findByEmailAndRefreshToken(email, dto.getRefreshToken()).orElseThrow(() -> new CustomException(ErrorCode.CANT_REFRESH_TOKEN));

        String accessToken = tokenProvider.generateAccessToken(user.getEmail(), user.getAuthorities());
        String refreshToken = tokenProvider.generateRefreshToken(user.getEmail(), user.getAuthorities());

        return new TokenDTO(accessToken, refreshToken);
    }
}