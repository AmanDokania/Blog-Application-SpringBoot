package com.springboot.blog.springbootblogrestapi.service.Impl;

import com.springboot.blog.springbootblogrestapi.entity.Role;
import com.springboot.blog.springbootblogrestapi.entity.User;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPiException;
import com.springboot.blog.springbootblogrestapi.payload.ChangePasswordDto;
import com.springboot.blog.springbootblogrestapi.payload.LoginDto;
import com.springboot.blog.springbootblogrestapi.payload.RegisterDto;
import com.springboot.blog.springbootblogrestapi.payload.UserDetailsDto;
import com.springboot.blog.springbootblogrestapi.repository.RoleRepository;
import com.springboot.blog.springbootblogrestapi.repository.UserRepository;
import com.springboot.blog.springbootblogrestapi.security.JwtTokenProvider;
import com.springboot.blog.springbootblogrestapi.service.AuthService;
import com.springboot.blog.springbootblogrestapi.util.AppConstants;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;
    private JwtTokenProvider jwtTokenProvider;
    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getUserNameOrEmail(),loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token=jwtTokenProvider.generateToken(authentication);
        return token;

//        return "user login successfully";
    }

    @Override
    public String register(RegisterDto registerDto) {

        System.out.println(registerDto.getEmail()+" "+registerDto.getUserName()+" "+registerDto.getProfileImage());
        // check if user is exists by username
        if(userRepository.existsByUsername(registerDto.getUserName()))
            throw new BlogAPiException(HttpStatus.BAD_REQUEST,"Username is already exist");
        if(userRepository.existsByEmail((registerDto.getEmail())))
            throw new BlogAPiException(HttpStatus.BAD_REQUEST,"user email is already exist");

        User user=new User();
        user.setName(registerDto.getName());
        user.setEmail(registerDto.getEmail());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setUsername(registerDto.getUserName());
        user.setProfileImage(registerDto.getProfileImage());
        System.out.println(user.getUsername()+" "+user.getEmail()+" "+user.getProfileImage());
        Set<Role> roles=new HashSet<>();

        Role userRole=roleRepository.findByName("ROLE_USER").orElseThrow(()->{
            System.out.println("error");
            return  new BlogAPiException(HttpStatus.BAD_REQUEST, "ERROR");
        });

        System.out.println("123456787654323456765432userRole.toString()");
        System.out.println(userRole.toString());
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        return "user registered successfully";
    }

    @Override
    public UserDetailsDto getCurrentUserDetails() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(username);
        User user = userRepository.findByUsername(username).orElseThrow( ()-> new BlogAPiException(HttpStatus.BAD_REQUEST, "User does not exist!."));
        UserDetailsDto userDetailsDto = new UserDetailsDto();
        userDetailsDto.setEmail(user.getEmail());
        userDetailsDto.setName(user.getName());
        userDetailsDto.setProfileImage(AppConstants.LOCAL_PATH_IMAGE+user.getProfileImage());
        userDetailsDto.setRoles(user.getRoles());
        userDetailsDto.setUsername(user.getUsername());
        System.out.println(user.getUsername()+" "+user.getEmail()+" "+userDetailsDto.getProfileImage());
        return  userDetailsDto;
    }

    @Override
    public String changePassword(ChangePasswordDto changePasswordDto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username).orElseThrow( ()-> new BlogAPiException(HttpStatus.BAD_REQUEST, "User does not exist!."));
        if(!passwordEncoder.matches(changePasswordDto.getOldPassword(), user.getPassword()))
        {

            throw new BlogAPiException(HttpStatus.BAD_REQUEST, "Password does not match");
        }

        user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
        userRepository.save(user);
        return "Change Password Successfully";
    }

}
