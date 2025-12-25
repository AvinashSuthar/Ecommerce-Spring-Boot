package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.model.Category;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CategoryServiceI {
    List<Category> getAllCategories();
    void createCategory(Category category);


    String deleteCategory(Long categoryId);

    Category updateCategory(Long categoryId, Category category);
}
