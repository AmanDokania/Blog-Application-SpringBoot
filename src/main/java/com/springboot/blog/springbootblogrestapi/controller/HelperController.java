package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.ChangePasswordDto;
import com.springboot.blog.springbootblogrestapi.payload.UserDetailsDto;
import com.springboot.blog.springbootblogrestapi.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class HelperController {

    private AuthService authService;

    @GetMapping(value = {"/userDetails" })
    public ResponseEntity<UserDetailsDto> userDetails()
    {
        UserDetailsDto userDetailsDto=authService.getCurrentUserDetails();
        return ResponseEntity.ok(userDetailsDto);
    }

    @PostMapping(value = {"/changePassword"})
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordDto changePasswordDto){
        return ResponseEntity.ok(authService.changePassword(changePasswordDto));
    }


}
