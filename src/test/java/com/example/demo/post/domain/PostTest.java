package com.example.demo.post.domain;

import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostTest {


    @Test
    public void PostCreate으로_Post를_생성할_수_있다(){
        //given
        PostCreate postCreate = PostCreate.builder()
                .writerId(1)
                .content("테스트게시물")
                .build();

       User writer =  User.builder()
                .email("jjjwodls@naver.com")
                .nickname("전기포트")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .address("seoul")
                .build();

       //when
        Post post = Post.from(postCreate, writer);

        //then
        assertThat(post.getContent()).isEqualTo("테스트게시물");
        assertThat(post.getWriter().getEmail()).isEqualTo("jjjwodls@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("전기포트");
        assertThat(post.getWriter().getAddress()).isEqualTo("seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");


    }


    @Test
    public void PostUpdate으로_Post를_생성할_수_있다(){

//        PostUpdate postupdate = PostUpdate.builder()

    }
}