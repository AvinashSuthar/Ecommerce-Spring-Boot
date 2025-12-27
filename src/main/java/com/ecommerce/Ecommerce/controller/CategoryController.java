package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.model.Category;
import com.ecommerce.Ecommerce.service.CategoryServiceI;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {
    @Autowired
    private CategoryServiceI categoryService;

    @GetMapping("/public/categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<Category> createCategory(@Valid @RequestBody Category category) {
        Category newCategory =  categoryService.createCategory(category);
        return new ResponseEntity<Category>(category, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long categoryId) {
        try {
           categoryService.deleteCategory(categoryId);
            return ResponseEntity.status(HttpStatus.OK).body("Deleted Successfully");
        } catch (ResponseStatusException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getReason());
        }
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<String> updateCategory(@PathVariable Long categoryId,@Valid @RequestBody Category category) {

            Category updatedCategory = categoryService.updateCategory(categoryId, category);
            return ResponseEntity.ok("Category Updated");

    }


}
