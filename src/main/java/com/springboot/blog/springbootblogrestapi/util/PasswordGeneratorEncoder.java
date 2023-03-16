package com.springboot.blog.springbootblogrestapi.util;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
public class PasswordGeneratorEncoder {

    public static void main(String[] args) {
         PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("aman"));
        System.out.println(passwordEncoder.encode("admin"));
    }

}
