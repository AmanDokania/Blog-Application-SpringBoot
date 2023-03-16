package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.payload.ChangePasswordDto;
import com.springboot.blog.springbootblogrestapi.payload.LoginDto;
import com.springboot.blog.springbootblogrestapi.payload.RegisterDto;
import com.springboot.blog.springbootblogrestapi.payload.UserDetailsDto;

public interface AuthService {

    String login(LoginDto loginDto);

    String register(RegisterDto registerDto);

    UserDetailsDto getCurrentUserDetails();

    String changePassword(ChangePasswordDto changePasswordDto);
}
