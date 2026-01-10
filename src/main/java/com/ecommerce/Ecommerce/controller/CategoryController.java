package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.config.AppConstants;
import com.ecommerce.Ecommerce.model.Category;
import com.ecommerce.Ecommerce.payload.CategoryDTO;
import com.ecommerce.Ecommerce.payload.CategoryResponse;
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
    public CategoryResponse getAllCategories(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER , required = false)  Integer pageNumber ,
                                             @RequestParam(name = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize ,
                                             @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY , required = false)  String sortBy,
                                             @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DIR , required = false)  String sortDir

    ) {
        return categoryService.getAllCategories(pageNumber, pageSize , sortBy, sortDir);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<CategoryDTO> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO newCategory =  categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(newCategory, HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long categoryId) {
           CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(categoryId);
           return ResponseEntity.status(HttpStatus.OK).body(deletedCategoryDTO);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId,@Valid @RequestBody CategoryDTO categoryDTO) {
            CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);
            return ResponseEntity.ok(updatedCategoryDTO);
    }
}
