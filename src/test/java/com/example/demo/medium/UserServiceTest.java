package com.example.demo.medium;

import com.example.demo.common.domain.exception.CertificationCodeNotMatchedException;
import com.example.demo.common.domain.exception.ResourceNotFoundException;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import com.example.demo.user.domain.UserCreate;
import com.example.demo.user.domain.UserUpdate;
import com.example.demo.user.infrastructure.UserEntity;
import com.example.demo.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockBean
    private JavaMailSender javaMailSender;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //given
        String email = "test@gmail.com";

        //when

        User user = userService.getByEmail(email);

        assertThat(user.getNickname()).isEqualTo("nickname");

    }

    @Test
    void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다() {
        //given
        String email = "test2@gmail.com";

        //when


        //then
        assertThatThrownBy(() -> {
            User result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);

    }


    @Test
    void getById은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //given

        //when

        User user = userService.getById(11);

        assertThat(user.getNickname()).isEqualTo("nickname");

    }

    @Test
    void getById은_PENDING_상태인_유저는_찾아올_수_없다() {
        //given

        //when


        //then
        assertThatThrownBy(() -> {
            User result = userService.getById(22);
        }).isInstanceOf(ResourceNotFoundException.class);

    }


    @Test
    void userCreateDto_를_이용하여_유저를_생성할_수_있다() {
        //given
        UserCreate userCreate = UserCreate.builder().email("jjjwodls@naver.com")
                .address("경기도")
                .nickname("전기포트")
                .build();

        BDDMockito.doNothing().when(javaMailSender).send(any(SimpleMailMessage.class));

        //when

        User result = userService.create(userCreate);

        //then
        assertThat(result.getId()).isNotNull();
        assertThat(result.getStatus()).isEqualTo(UserStatus.PENDING);

    }

    @Test
    void userUpdateDto_를_이용하여_유저를_수정할_수_있다() {
        //given
        UserUpdate userUpdate = UserUpdate.builder()
                .address("하남")
                .nickname("전기주전자")
                .build();


        //when

        userService.update(11, userUpdate);

        //then
        User user = userService.getById(11);

        assertThat(user.getId()).isNotNull();
        assertThat(user.getAddress()).isEqualTo("하남");
        assertThat(user.getNickname()).isEqualTo("전기주전자");

    }

    @Test
    void user를_로그인시키면_마지막_로그인_시간이_변경된다() {
        //given

        //when

        userService.login(11);

        //then
        User user = userService.getById(11);
        assertThat(user.getLastLoginAt()).isGreaterThan(0L);

    }

    @Test
    void PENDING_상태의_사용자는_인증_코드로_ACTIVE_시킬_수_있다() {
        //given

        //when

        userService.verifyEmail(22,"aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaab");

        //then
        User user = userService.getById(22);
        assertThat(user.getStatus()).isEqualTo(UserStatus.ACTIVE);

    }

    @Test
    void PENDING_상태의_사용자는_잘못된_인증_코드를_받으면_에러를_던진다() {
        //given

        //when



        //then

        assertThatThrownBy(() -> {
            userService.verifyEmail(22, "aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaac");
        }).isInstanceOf(CertificationCodeNotMatchedException.class);


    }



}