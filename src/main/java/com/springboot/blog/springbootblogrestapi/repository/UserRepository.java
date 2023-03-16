package com.springboot.blog.springbootblogrestapi.repository;

import com.springboot.blog.springbootblogrestapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String name);
    Optional<User> findByUsernameOrEmail(String name,String email);

    Boolean existsByUsername(String name);
    Boolean existsByEmail(String email);
}
