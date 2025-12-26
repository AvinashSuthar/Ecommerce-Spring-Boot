package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.model.Category;

import java.util.List;

public interface CategoryServiceI {
    List<Category> getAllCategories();
    Category createCategory(Category category);


    void deleteCategory(Long categoryId);

    Category updateCategory(Long categoryId, Category category);
}
