package com.springboot.blog.springbootblogrestapi.service.Impl;

import com.springboot.blog.springbootblogrestapi.entity.Category;
import com.springboot.blog.springbootblogrestapi.exception.ResourceNotFoundException;
import com.springboot.blog.springbootblogrestapi.mapper.CategoryMapper;
import com.springboot.blog.springbootblogrestapi.payload.CategoryDTo;
import com.springboot.blog.springbootblogrestapi.repository.CategoryRepository;
import com.springboot.blog.springbootblogrestapi.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;
    @Override
    public CategoryDTo addCategory(CategoryDTo categoryDTo) {
        Category category= CategoryMapper.MAPPER.mapToCategory(categoryDTo);
        Category savedCategory=categoryRepository.save(category);
        return CategoryMapper.MAPPER.mapToCategoryDto(savedCategory);
    }

    @Override
    public CategoryDTo getCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("category","id",String.valueOf(categoryId)));
        return CategoryMapper.MAPPER.mapToCategoryDto(category);
    }

    @Override
    public List<CategoryDTo> getAllCategory() {
        List<Category> categories=categoryRepository.findAll();
        return categories.stream().map((category -> CategoryMapper.MAPPER.mapToCategoryDto(category)))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTo updateCategory(CategoryDTo categoryDTo,Long categoryId) {
        Category category=categoryRepository.findById(categoryId)
                .orElseThrow(()->new ResourceNotFoundException("category","id",String.valueOf(categoryId)));

        category.setName(categoryDTo.getName());
        category.setDescription(categoryDTo.getDescription());
        Category updatedCategory= categoryRepository.save(category);
        return CategoryMapper.MAPPER.mapToCategoryDto(updatedCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        Category category=categoryRepository.findById(categoryId).
                orElseThrow(()-> new ResourceNotFoundException("category","id",String.valueOf(categoryId)));

        categoryRepository.delete(category);
    }
}
