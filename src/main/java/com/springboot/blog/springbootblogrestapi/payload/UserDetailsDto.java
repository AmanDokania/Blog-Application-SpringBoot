package com.springboot.blog.springbootblogrestapi.payload;

import com.springboot.blog.springbootblogrestapi.entity.Role;
import lombok.*;
import org.springframework.stereotype.Service;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserDetailsDto {
    private String name ;
    private String username ;
    private String email;

    private String profileImage;
    private Set<Role> roles;
}
