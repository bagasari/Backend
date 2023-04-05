package com.bagasari.sacbagaji.api.service;

import java.util.Collections;

import com.bagasari.sacbagaji.api.dto.UserDTO;
import com.bagasari.sacbagaji.api.repository.UserRepository;
import com.bagasari.sacbagaji.domain.Authority;
import com.bagasari.sacbagaji.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입 서비스 로직
     */
    @Transactional
    public String signup(UserDTO userDto) {
        if (userRepository.findOneWithAuthoritiesByEmail(userDto.getEmail()).orElse(null) != null) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다.");
        }

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User user = User.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .name(userDto.getName())
                .authorities(Collections.singleton(authority))
                .build();

        userRepository.save(user);

        return "로그인 성공";
    }
}