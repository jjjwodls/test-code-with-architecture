package com.example.demo.user.domain;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.mock.TestUuidHolder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {


    @Test
    public void USER는_UserCreate_객체로_생성할_수_있다() {
        //given
        UserCreate userCreate = UserCreate.builder()
                .email("jjjwodls@naver.com")
                .address("경기도")
                .nickname("전기포트")
                .build();
        //when
        User user = User.from(userCreate, new TestUuidHolder("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa"));
        //then

        assertThat(user.getId()).isNull();
        assertThat(user.getEmail()).isEqualTo("jjjwodls@naver.com");
        assertThat(user.getAddress()).isEqualTo("경기도");
        assertThat(user.getNickname()).isEqualTo("전기포트");
        assertThat(user.getStatus()).isEqualTo(UserStatus.PENDING);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");




    }

    @Test
    public void USER는_Userupdate_객체로_생성할_수_있다() {
        //given
        //given
        User user =  User.builder()
                .id(1L)
                .email("jjjwodls@naver.com")
                .nickname("전기포트")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .address("seoul")
                .lastLoginAt(100L)
                .build();

        UserUpdate userUpdate = UserUpdate.builder()
                .address("서울")
                .nickname("업데이트 닉네임")
                .build();


        //when
        user = user.update(userUpdate);
        //then

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getAddress()).isEqualTo("서울");
        assertThat(user.getNickname()).isEqualTo("업데이트 닉네임");
        assertThat(user.getEmail()).isEqualTo("jjjwodls@naver.com");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");


    }

    @Test
    public void USER는_마지막_로그인한_시간을_현재시간으로_지정할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("jjjwodls@naver.com")
                .nickname("전기포트")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .address("seoul")
                .lastLoginAt(100L)
                .build();
        //when
        user = user.login(new TestClockHolder(200L));
        //then

        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getAddress()).isEqualTo("seoul");
        assertThat(user.getNickname()).isEqualTo("전기포트");
        assertThat(user.getEmail()).isEqualTo("jjjwodls@naver.com");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(user.getLastLoginAt()).isEqualTo(200L);


    }


    @Test
    public void USER는_인증코드로_유저를_활성화_할_수_있다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("jjjwodls@naver.com")
                .nickname("전기포트")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.PENDING)
                .address("seoul")
                .lastLoginAt(100L)
                .build();
        //when
        user = user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        //then
        assertThat(user.getId()).isEqualTo(1L);
        assertThat(user.getAddress()).isEqualTo("seoul");
        assertThat(user.getNickname()).isEqualTo("전기포트");
        assertThat(user.getEmail()).isEqualTo("jjjwodls@naver.com");
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(user.getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(user.getLastLoginAt()).isEqualTo(100L);

    }

    @Test
    public void USER는_인증코드가_잘못되면_인증에러_예외를_뱉는다() {
        //given
        User user = User.builder()
                .id(1L)
                .email("jjjwodls@naver.com")
                .nickname("전기포트")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.PENDING)
                .address("seoul")
                .lastLoginAt(100L)
                .build();
        //when
        //then
        assertThatThrownBy(() -> {
            user.certificate("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);

    }

}