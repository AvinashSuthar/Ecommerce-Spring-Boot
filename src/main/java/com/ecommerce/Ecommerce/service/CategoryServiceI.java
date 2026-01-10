package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.payload.CategoryDTO;
import com.ecommerce.Ecommerce.payload.CategoryResponse;

public interface CategoryServiceI {
    CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
    CategoryDTO createCategory(CategoryDTO categoryDTO);


    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);
}
