package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.exceptions.APIException;
import com.ecommerce.Ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.Ecommerce.model.Category;
import com.ecommerce.Ecommerce.respositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceI {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {

        List<Category> categories =  categoryRepository.findAll();
        if(categories.isEmpty()) throw new APIException("No categories are created till now");
        return categories;
    }

    @Override
    public Category createCategory(Category category) {
        Category category1 = categoryRepository.findByCategoryName(category.getCategoryName());
        if(category1 != null) throw new APIException("Category Already exists with " + category1.getCategoryName() + " name");
        return categoryRepository.save(category);
    }

    @Override
    public void deleteCategory(Long categoryId) throws IllegalArgumentException {
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(category.getCategoryName());
            return categoryRepository.save(existingCategory);
        }
        throw  new ResourceNotFoundException("Category" , "CategoryId" , categoryId);
    }
}
