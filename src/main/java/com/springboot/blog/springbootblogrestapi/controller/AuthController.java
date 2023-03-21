package com.springboot.blog.springbootblogrestapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.blog.springbootblogrestapi.entity.Image;

import com.springboot.blog.springbootblogrestapi.payload.JwtAuthResponse;
import com.springboot.blog.springbootblogrestapi.payload.LoginDto;
import com.springboot.blog.springbootblogrestapi.payload.RegisterDto;
import com.springboot.blog.springbootblogrestapi.repository.ImageRepositroy;
import com.springboot.blog.springbootblogrestapi.service.AuthService;
import com.springboot.blog.springbootblogrestapi.util.ImageUtil;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")

public class AuthController {

    @Autowired
    private AuthService authService;

    @Value("${project.image}")
    private String path;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ImageRepositroy imageRepositroy;

    @PostMapping(value = {"/login","/signin"})
    private ResponseEntity<JwtAuthResponse> login(@RequestBody LoginDto loginDto){
        String token=authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);
        return new ResponseEntity<>(jwtAuthResponse, HttpStatus.OK);
    }

    @PostMapping(value = {"/register","/signup"})
    private ResponseEntity<String> signup(@RequestBody RegisterDto registerDto){
        String result=authService.register(registerDto);
        System.out.println(result);
        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    //Build Register Rest API
    @PostMapping(value = {"/v2/signup" , "/v2/register"})
    public ResponseEntity<String> signUp(@Valid @RequestParam("data") String data,
                                         @RequestParam("file") MultipartFile file) throws IOException
    {

        // file name
         String name=file.getOriginalFilename();

         // get random string
      //  String random= UUID.randomUUID().toString();

        System.out.println(name.substring(name.lastIndexOf(".")));
//        random=random+(name.substring(name.lastIndexOf(".")));
//        String fullpath=path+File.separator+random;
//        File f=new File(path);
//        System.out.println(f.exists());
//        if(!f.exists()){
//            f.mkdir();
//        }
        // File path
//        String UPLOAD_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();
//
//
//        String fileName =  (  "" + System.currentTimeMillis() + file.getOriginalFilename() );
//        // Save file
//        Files.copy(file.getInputStream(), Paths.get(UPLOAD_DIR + File.separator + fileName),
//                StandardCopyOption.REPLACE_EXISTING);
//
//        // get file uri
//        String uri = ServletUriComponentsBuilder.fromCurrentContextPath().path("/images/")
//                .path(fileName).toUriString();


        Image image =imageRepositroy.save(Image.builder()
                .name(System.currentTimeMillis()+file.getOriginalFilename())
                .type(file.getContentType())
                .imageData(ImageUtil.compressImage(file.getBytes())).build());

        // converting string to json
        RegisterDto registerDto = objectMapper.readValue(data, RegisterDto.class);

        // converting string to json
       // System.out.println(data);
      //  RegisterDto registerDto = Mapper.MAPPER.mapToRegisterDto(data);
//        System.out.println(registerDto.getEmail()+" "+registerDto.getName());
//        System.out.println(fullpath);
        registerDto.setProfileImage(image.getName());

        String response = authService.register(registerDto);
//        Files.copy(file.getInputStream(),Paths.get(fullpath),StandardCopyOption.REPLACE_EXISTING);
        return ResponseEntity.ok(response);
    }


}
