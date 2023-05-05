package com.bagasari.sacbagaji;

import com.bagasari.sacbagaji.model.entity.Authority;
import com.bagasari.sacbagaji.model.entity.User;
import com.bagasari.sacbagaji.repository.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Collections;

@SpringBootTest
@AutoConfigureMockMvc
@Rollback
@Transactional
public class BaseTest {

    @Autowired
    protected MockMvc mockmvc;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected ProductRepository productRepository;

    @Autowired
    protected AccountBookRepository accountBookRepository;

    @Autowired
    protected DestinationRepository destinationRepository;

    @Autowired
    protected AuthorityRepository authorityRepository;

    protected final String testMail = "test@gmail.com";
    protected final String testPassword = "testPassword";
    protected final String testName = "testName";

    @BeforeEach
    void setUp() {
        authorityRepository.save(new Authority("ROLE_USER"));

        Authority authority = Authority.builder()
                .authorityName("ROLE_USER")
                .build();

        User testUser = User.builder()
                .email(testMail)
                .password(passwordEncoder.encode(testPassword))
                .name(testName)
                .authorities(Collections.singleton(authority))
                .build();

        userRepository.save(testUser);
    }
}
