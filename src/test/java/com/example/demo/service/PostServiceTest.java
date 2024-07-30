package com.example.demo.service;

import com.example.demo.model.dto.PostCreateDto;
import com.example.demo.model.dto.PostUpdateDto;
import com.example.demo.repository.PostEntity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@SqlGroup({
        @Sql(value = "/sql/post-service-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(value = "/sql/delete-all-data.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
})
class PostServiceTest {

    @Autowired
    private PostService postService;

    @Test
    void getById를_통해_post_data_를_조회할_수_있다() {
        //given
        //when
        PostEntity post = postService.getById(2);

        //then
        assertThat(post.getContent()).isEqualTo("게시물컨텐츠");
        assertThat(post.getWriter().getEmail()).isEqualTo("test@gmail.com");

    }

    @Test
    void create() {

        //given

        PostCreateDto postCreateDto = PostCreateDto.builder()
                .writerId(2)
                .content("테스트게시물")
                .build();

        //when
        PostEntity post = postService.create(postCreateDto);

        //then
        assertThat(post.getId()).isNotNull();
        assertThat(post.getContent()).isEqualTo("테스트게시물");
        assertThat(post.getCreatedAt()).isGreaterThan(0);


    }

    @Test
    void update() {

        //given
        PostUpdateDto postUpdateDto = PostUpdateDto.builder().content("업데이트").build();


        //when

        postService.update(2, postUpdateDto);

        //then

        PostEntity post = postService.getById(2);
        assertThat(post.getContent()).isEqualTo("업데이트");
        assertThat(post.getModifiedAt()).isGreaterThan(0);
    }
}