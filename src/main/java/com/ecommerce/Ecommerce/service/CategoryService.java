package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceI {
    List<Category> categories = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categories.stream()
                .filter((c) -> c.getCategoryId().equals(categoryId))
                .findFirst().orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found !"));
        categories.remove(category);
        return "Category removed successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> existingCategory = categories.stream()
                .filter((c) -> c.getCategoryId().equals(categoryId))
                .findFirst();
        if (existingCategory.isPresent()) {
            existingCategory.get().setCategoryName(category.getCategoryName());
            return existingCategory.get();
        } else {
            return null;
        }
    }

}
