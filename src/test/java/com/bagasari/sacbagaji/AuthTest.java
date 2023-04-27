package com.bagasari.sacbagaji;

import com.bagasari.sacbagaji.model.dto.req.SignInRequestDTO;
import com.bagasari.sacbagaji.model.dto.req.SignUpRequestDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class AuthTest extends BaseTest {

    @Test
    @DisplayName("회원가입 성공")
    public void signUpTest() throws Exception {
        //given
        SignUpRequestDTO dto = new SignUpRequestDTO("testInput@gmail.com", "testInputPwd", "testInputName");

        //when
        ResultActions rst = mockmvc.perform(post("/v1/auth/signUp")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );

        //then
        rst.andExpect(status().isOk());
    }

    //TODO(andExpect 조건 추가해야함)
    @Test
    @DisplayName("로그인 성공")
    public void signInTest() throws Exception {
        //given
        SignInRequestDTO dto = new SignInRequestDTO(testMail, testPassword);

        //when
        ResultActions rst = mockmvc.perform(post("/v1/auth/signIn")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto))
        );

        //then
        rst
                .andExpect(status().isOk())
                .andExpect(jsonPath("token").exists());
    }

}
