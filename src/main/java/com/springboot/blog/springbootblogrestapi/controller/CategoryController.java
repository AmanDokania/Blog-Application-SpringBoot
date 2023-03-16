package com.springboot.blog.springbootblogrestapi.controller;

import com.springboot.blog.springbootblogrestapi.payload.CategoryDTo;
import com.springboot.blog.springbootblogrestapi.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {

    private CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTo> createCategory(@RequestBody  CategoryDTo categoryDTo){
        CategoryDTo savedCategory=categoryService.addCategory(categoryDTo);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<CategoryDTo> getCategory(@PathVariable("id") Long categoryId){
        CategoryDTo categoryDTo=categoryService.getCategory(categoryId);
        return new ResponseEntity<>(categoryDTo,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTo>> getAllCategory(){
        List<CategoryDTo>categoryDTos=categoryService.getAllCategory();
        return new ResponseEntity<>(categoryDTos,HttpStatus.OK);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CategoryDTo> updateCategory(@RequestBody CategoryDTo categoryDTo,@PathVariable("id") Long categoryId){
        CategoryDTo updatedCategory=categoryService.updateCategory(categoryDTo,categoryId);
        return new ResponseEntity<>(updatedCategory,HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteCategory(@PathVariable("id") Long categoryId){
        categoryService.deleteCategory(categoryId);
        return  ResponseEntity.ok("Category deleted successfully");
    }
}
