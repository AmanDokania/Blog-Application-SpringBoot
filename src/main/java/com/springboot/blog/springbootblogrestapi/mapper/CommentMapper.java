package com.springboot.blog.springbootblogrestapi.mapper;

import com.springboot.blog.springbootblogrestapi.entity.Comment;
import com.springboot.blog.springbootblogrestapi.payload.CommentDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {

    CommentMapper COMMENT_MAPPER= Mappers.getMapper(CommentMapper.class);

    CommentDto mapToCommentDto(Comment comment);

    Comment mapToComment(CommentDto commentDto);
}
