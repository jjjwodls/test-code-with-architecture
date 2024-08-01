package com.example.demo.post.domain;

import com.example.demo.common.service.port.ClockHolder;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

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
        Post post = Post.from(postCreate, writer, new TestClockHolder(100L));

        //then
        assertThat(post.getContent()).isEqualTo("테스트게시물");
        assertThat(post.getWriter().getEmail()).isEqualTo("jjjwodls@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("전기포트");
        assertThat(post.getWriter().getAddress()).isEqualTo("seoul");
        assertThat(post.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);
        assertThat(post.getWriter().getCertificationCode()).isEqualTo("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa");
        assertThat(post.getCreatedAt()).isEqualTo(100L);


    }


    @Test
    public void PostUpdate으로_Post를_생성할_수_있다(){

        //given
        PostUpdate postUpdate = PostUpdate.builder()
                .content("업데이트 게시물")
                .build();

        User writer =  User.builder()
                .email("jjjwodls@naver.com")
                .nickname("전기포트")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .status(UserStatus.ACTIVE)
                .address("seoul")
                .build();

        Post post = Post.builder()
                .id(1L)
                .content("변경전")
                .writer(writer)
                .createdAt(123124523523L)
                .modifiedAt(0L)
                .build();

        //when
        post = post.update(postUpdate,  new TestClockHolder(100L));

        //then
        assertThat(post.getContent()).isEqualTo("업데이트 게시물");
        assertThat(post.getWriter().getEmail()).isEqualTo("jjjwodls@naver.com");
        assertThat(post.getWriter().getNickname()).isEqualTo("전기포트");
        assertThat(post.getWriter().getAddress()).isEqualTo("seoul");
        assertThat(post.getModifiedAt()).isEqualTo(100L);


    }
}