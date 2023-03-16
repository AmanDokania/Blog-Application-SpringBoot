package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class CommentController {

    private CommentService commentService;

    @PostMapping("/posts/{id}/comments")
    private ResponseEntity<CommentDto> createComment(@PathVariable("id") Long postId, @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.createComment(postId,commentDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts/{id}/comments")
    private ResponseEntity<List<CommentDto>> getComments(@PathVariable("id") Long postId){
        return new ResponseEntity<>(commentService.getCommentByPostId(postId),HttpStatus.OK);
    }

    @GetMapping("/posts/{pId}/comments/{cId}")
    private ResponseEntity<CommentDto> getCommentById(@PathVariable("pId") Long postId,@PathVariable("cId") Long commentId){
        return new ResponseEntity<>(commentService.getCommentById(postId,commentId),HttpStatus.OK);
    }

    @PutMapping("/posts/{pId}/comments/{cId}")
    private ResponseEntity<CommentDto> updateComment(@PathVariable("pId") Long postId,
                                                     @PathVariable("cId") Long commentId,
                                                     @Valid @RequestBody CommentDto commentDto){
        return new ResponseEntity<>(commentService.updateComment(postId,commentId,commentDto),HttpStatus.OK);
    }

    @DeleteMapping("/posts/{pId}/comments/{cId}")
    private ResponseEntity<String> deleteComment(@PathVariable("pId") Long postId,
                                                 @PathVariable("cId") Long commentId){
        commentService.deleteComment(postId,commentId);
        return new ResponseEntity<>("Comment deleted Successfully",HttpStatus.OK);
    }
}
