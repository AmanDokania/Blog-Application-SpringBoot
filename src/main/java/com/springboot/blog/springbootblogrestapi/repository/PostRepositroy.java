package com.springboot.blog.springbootblogrestapi.repository;

import com.springboot.blog.springbootblogrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepositroy extends JpaRepository<Post,Long> {

    List<Post> findByCategoryId(Long categoryId);

    List<Post> findByCreatedBy(String username);
}
