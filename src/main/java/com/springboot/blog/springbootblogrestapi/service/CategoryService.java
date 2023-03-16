package com.springboot.blog.springbootblogrestapi.service;

import com.springboot.blog.springbootblogrestapi.payload.CategoryDTo;

import java.util.List;

public interface CategoryService {

    CategoryDTo addCategory(CategoryDTo categoryDTo);

    CategoryDTo getCategory(Long categoryId);

    List<CategoryDTo> getAllCategory();

    CategoryDTo updateCategory(CategoryDTo categoryDTo,Long categoryId);

    void deleteCategory(Long categoryId);
}
