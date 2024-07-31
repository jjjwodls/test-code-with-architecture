package com.example.demo.user.controller.dto;

import com.example.demo.post.domain.PostCreate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class UserResponseTest {


    @Test
    public void User으로_응답을_생성할_수_있다(){
        //given
        User user =  User.builder()
                .email("jjjwodls@naver.com")
                .nickname("전기포트")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .address("seoul")
                .lastLoginAt(100L)
                .build();
        //when
        UserResponse from = UserResponse.from(user);

        //then

        assertThat(from.getEmail()).isEqualTo("jjjwodls@naver.com");
        assertThat(from.getNickname()).isEqualTo("전기포트");
        assertThat(from.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(from.getLastLoginAt()).isEqualTo(100L);


    }

}