package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.PostResponse;
import com.springboot.blog.springbootblogrestapi.service.PostService;
import com.springboot.blog.springbootblogrestapi.util.AppConstants;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/posts")
@AllArgsConstructor
public class PostController {

    private PostService postService;

//    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<PostDto> createPost(@Valid  @RequestBody  PostDto postDto){
        System.out.println(postDto.toString());
        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostResponse> getAllPost(
            @RequestParam(value = "pageNo",defaultValue = AppConstants.DEFAULT_PAGE_NUMBER,required = false)int pageNo,
            @RequestParam(value = "pageSize",defaultValue = AppConstants.DEFAULT_PAGE_SIZE,required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = AppConstants.DEFAULT_SORT_BY,required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = AppConstants.DEFAULT_SORT_DIRECTION,required = false)String sortDir
    ){
        return new ResponseEntity<>(postService.getAllPOst(pageNo,pageSize,sortBy,sortDir),HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable ("id") Long postId){
        return  ResponseEntity.ok(postService.getPostById(postId));
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable ("id") Long postId,@Valid @RequestBody PostDto postDto){
        return ResponseEntity.ok(postService.updatePost(postDto,postId));
    }

//    @PreAuthorize("hasRole('ADMIN')")
//    @DeleteMapping("{id}")
//    public ResponseEntity<String> deletePost(@PathVariable ("id") Long postId){
//        postService.deletePostById(postId);
//        return new ResponseEntity<>("post entity deleted  successfuly",HttpStatus.OK);
//    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<PostDto>> getPostByCategoryId(@PathVariable("id") Long categoryId){
        return new ResponseEntity<>(postService.getPostByCategoryId(categoryId),HttpStatus.OK);
    }

    @GetMapping("/myblog")
    public ResponseEntity<List<PostDto>> getPostofMyblog(){
        return new ResponseEntity<>(postService.getPostOfMyblog(),HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePost(@PathVariable ("id") Long postId){
        postService.deletePostByIdOfSelf(postId);
        return new ResponseEntity<>("post deleted  successfully",HttpStatus.OK);
    }

}
