package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.config.AppConstants;
import com.ecommerce.Ecommerce.model.Category;
import com.ecommerce.Ecommerce.payload.APIResponse;
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
    public ResponseEntity<APIResponse<CategoryResponse>> getAllCategories(@RequestParam(name = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER , required = false)  Integer pageNumber ,
                                                        @RequestParam(name = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize ,
                                                        @RequestParam(name = "sortBy", defaultValue = AppConstants.SORT_CATEGORIES_BY , required = false)  String sortBy,
                                                        @RequestParam(name = "sortDir", defaultValue = AppConstants.SORT_DIR , required = false)  String sortDir

    ) {
        CategoryResponse categoryResponse = categoryService.getAllCategories(pageNumber, pageSize , sortBy, sortDir);
        return new ResponseEntity<>(new APIResponse<>("Fetched Categories" , true, categoryResponse) , HttpStatus.OK);
    }

    @PostMapping("/admin/categories")
    public ResponseEntity<APIResponse<CategoryDTO>> createCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO newCategory =  categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(new APIResponse<>("Category created successfully" , true , newCategory), HttpStatus.CREATED);
    }

    @DeleteMapping("/admin/categories/{categoryId}")
    public ResponseEntity<APIResponse<CategoryDTO>> deleteCategory(@PathVariable Long categoryId) {
           CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(categoryId);
           return new ResponseEntity<>(new APIResponse<>("Category deleted successfully" , true , deletedCategoryDTO), HttpStatus.CREATED);
    }

    @PutMapping("/admin/categories/{categoryId}")
    public ResponseEntity<APIResponse<CategoryDTO>>  updateCategory(@PathVariable Long categoryId,@Valid @RequestBody CategoryDTO categoryDTO) {
            CategoryDTO updatedCategoryDTO = categoryService.updateCategory(categoryId, categoryDTO);
            return new ResponseEntity<>(new APIResponse<>("Category updated successfully" , true , updatedCategoryDTO), HttpStatus.CREATED);
    }
}
