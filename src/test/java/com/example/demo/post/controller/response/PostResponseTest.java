package com.example.demo.post.controller.response;

import com.example.demo.post.domain.Post;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PostResponseTest {


    @Test
    public void Post으로_응답을_생성할_수_있다(){
        //given

        Post post = Post.builder()
                .content("hello_post")
                .writer(

                        User.builder()
                                .email("jjjwodls@naver.com")
                                .nickname("전기포트")
                                .status(UserStatus.ACTIVE)
                                .build()
                )
                .build();


        //when

        PostResponse postResponse = PostResponse.from(post);
        //then

        assertThat(postResponse.getContent()).isEqualTo("hello_post");
        assertThat(postResponse.getWriter().getEmail()).isEqualTo("jjjwodls@naver.com");
        assertThat(postResponse.getWriter().getNickname()).isEqualTo("전기포트");
        assertThat(postResponse.getWriter().getStatus()).isEqualTo(UserStatus.ACTIVE);

    }
}