package com.example.demo.post.service;

import com.example.demo.mock.FakePostRepository;
import com.example.demo.mock.FakeUserRepository;
import com.example.demo.mock.TestClockHolder;
import com.example.demo.post.domain.Post;
import com.example.demo.post.domain.PostCreate;
import com.example.demo.post.domain.PostUpdate;
import com.example.demo.user.domain.User;
import com.example.demo.user.domain.UserStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

import static org.assertj.core.api.Assertions.assertThat;

class PostServiceTest {

    private PostService postService;

    @BeforeEach
    public void init(){
        FakePostRepository fakePostRepository = new FakePostRepository();
        FakeUserRepository fakeUserRepository = new FakeUserRepository();

        postService = PostService.builder()
                .userRepository(fakeUserRepository)
                .postRepository(fakePostRepository)
                .clockHolder(new TestClockHolder(100L))
                .build();

        User user = User.builder().
                id(2L)
                .address("서울")
                .certificationCode("aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa")
                .email("test@gmail.com")
                .lastLoginAt(0L)
                .nickname("nickname")
                .status(UserStatus.ACTIVE)
                .build();

        fakeUserRepository.save(user);

        fakePostRepository.save(
                Post.builder()
                        .id(2L)
                        .content("게시물컨텐츠")
                        .createdAt(0L)
                        .modifiedAt(0L)
                        .writer(user)
                        .build()
        );
    }

//    insert into users (id, address, certification_code, email, last_login_at, nickname, status)
//    values (2, '서울','aaaaaaaa-aaaa-aaaa-aaaa-aaaaaaaaaaaa', 'test@gmail.com', '0', 'nickname', 'ACTIVE');
//
//    insert into posts (id, content, created_at, modified_at, user_id)
//    values (2, '게시물컨텐츠','0', '0', 2);


    @Test
    void getById를_통해_post_data_를_조회할_수_있다() {
        //given
        //when
        Post post = postService.getById(2);

        //then
        assertThat(post.getContent()).isEqualTo("게시물컨텐츠");
        assertThat(post.getWriter().getEmail()).isEqualTo("test@gmail.com");

    }

    @Test
    void create() {

        //given

        PostCreate postCreate = PostCreate.builder()
                .writerId(2)
                .content("테스트게시물")
                .build();

        //when
        Post post = postService.create(postCreate);

        //then
        assertThat(post.getId()).isNotNull();
        assertThat(post.getContent()).isEqualTo("테스트게시물");
        assertThat(post.getCreatedAt()).isEqualTo(100L);


    }

    @Test
    void update() {

        //given
        PostUpdate postUpdate = PostUpdate.builder().content("업데이트").build();


        //when

        postService.update(2, postUpdate);

        //then

        Post post = postService.getById(2);
        assertThat(post.getContent()).isEqualTo("업데이트");
        assertThat(post.getModifiedAt()).isEqualTo(100L);
    }
}