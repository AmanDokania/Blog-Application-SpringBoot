package com.springboot.blog.springbootblogrestapi.mapper;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.payload.CategoryDTo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {

    CategoryMapper MAPPER= Mappers.getMapper(CategoryMapper.class);

    CategoryDTo mapToCategoryDto(Category category);

    Category mapToCategory(CategoryDTo categoryDTo);

}
