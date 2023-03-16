package com.springboot.blog.springbootblogrestapi.security;

import com.springboot.blog.springbootblogrestapi.entity.User;
import com.springboot.blog.springbootblogrestapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userNameOrEmail) throws UsernameNotFoundException {

        User user=userRepository.findByUsernameOrEmail(userNameOrEmail,userNameOrEmail)
                .orElseThrow(()->new UsernameNotFoundException("user not found with email and email  "+userNameOrEmail));


        Set<GrantedAuthority> authoritySet=user.getRoles().
                stream().map((role -> new SimpleGrantedAuthority(role.getName()))).collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(user.getName(),user.getPassword(),authoritySet);
    }
}
