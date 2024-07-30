package com.example.demo.service;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UserEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/user-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void getByEmail은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //given
        String email = "test@gmail.com";

        //when

        UserEntity user = userService.getByEmail(email);

        assertThat(user.getNickname()).isEqualTo("nickname");

    }

    @Test
    void getByEmail은_PENDING_상태인_유저는_찾아올_수_없다() {
        //given
        String email = "test2@gmail.com";

        //when


        //then
        assertThatThrownBy(() -> {
            UserEntity result = userService.getByEmail(email);
        }).isInstanceOf(ResourceNotFoundException.class);

    }


    @Test
    void getById은_ACTIVE_상태인_유저를_찾아올_수_있다() {
        //given

        //when

        UserEntity user = userService.getById(1);

        assertThat(user.getNickname()).isEqualTo("nickname");

    }

    @Test
    void getById은_PENDING_상태인_유저는_찾아올_수_없다() {
        //given

        //when


        //then
        assertThatThrownBy(() -> {
            UserEntity result = userService.getById(2);
        }).isInstanceOf(ResourceNotFoundException.class);

    }

}