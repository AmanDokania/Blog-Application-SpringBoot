package com.springboot.blog.springbootblogrestapi.service.Impl;

import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.exception.BlogAPiException;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.mapper.CommentMapper;
import com.springboot.blog.springbootblogrestapi.mapper.Mapper;
import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import com.springboot.blog.springbootblogrestapi.repository.CommentRepository;
import com.springboot.blog.springbootblogrestapi.repository.PostRepositroy;
import com.springboot.blog.springbootblogrestapi.service.CommentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private PostRepositroy postRepositroy;
    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {
        Comment comment= CommentMapper.COMMENT_MAPPER.mapToComment(commentDto);

        Post post= postRepositroy.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));

        comment.setPost(post);
        Comment newComment = commentRepository.save(comment);
        System.out.println(post);

        return CommentMapper.COMMENT_MAPPER.mapToCommentDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentByPostId(Long postId) {
        List<Comment> commentList=commentRepository.findByPostId(postId);

        return commentList.stream().map(comment -> CommentMapper.COMMENT_MAPPER.mapToCommentDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {
        Post post= postRepositroy.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPiException(HttpStatus.BAD_REQUEST,"comment doesn't belong to post");

        return CommentMapper.COMMENT_MAPPER.mapToCommentDto(comment);
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post= postRepositroy.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPiException(HttpStatus.BAD_REQUEST,"comment doesn't belong to post");

//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment= commentRepository.save(comment);
        return CommentMapper.COMMENT_MAPPER.mapToCommentDto(updatedComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {
        Post post= postRepositroy.findById(postId).orElseThrow(()->new ResourceNotFoundException("post","id",postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFoundException("comment","id",commentId));

        if(!comment.getPost().getId().equals(post.getId()))
            throw new BlogAPiException(HttpStatus.BAD_REQUEST,"comment doesn't belong to post");

        commentRepository.delete(comment);
    }


}
