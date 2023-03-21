package com.springboot.blog.springbootblogrestapi.mapper;


import com.springboot.blog.springbootblogrestapi.entity.Post;
import com.springboot.blog.springbootblogrestapi.payload.PostDto;
import com.springboot.blog.springbootblogrestapi.payload.RegisterDto;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface Mapper {

    Mapper MAPPER = Mappers.getMapper(Mapper.class);

    @Mapping(source = "category.id",target = "categoryId")
    PostDto mapToPostDto(Post post);

    Post mapToPost(PostDto postDto);

    RegisterDto mapToRegisterDto(String data);


}
