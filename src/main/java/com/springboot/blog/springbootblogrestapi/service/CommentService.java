package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CommentService {
    public CommentDto createComment(Long postId,CommentDto commentDto);

    public List<CommentDto> getCommentByPostId(Long postId);

    public CommentDto getCommentById(Long postId,Long commentId);

    public CommentDto updateComment(Long postId,Long commentId,CommentDto commentDto);

    public void  deleteComment(Long postId,Long commentId);
}
