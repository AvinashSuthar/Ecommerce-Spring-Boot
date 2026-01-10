package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.exceptions.APIException;
import com.ecommerce.Ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.Ecommerce.model.Category;
import com.ecommerce.Ecommerce.payload.CategoryDTO;
import com.ecommerce.Ecommerce.payload.CategoryResponse;
import com.ecommerce.Ecommerce.respositories.CategoryRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements CategoryServiceI {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CategoryResponse getAllCategories(Integer pageNumber, Integer pageSize, String sortBy , String sortDir) {
        Sort sortByAndOrder = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize , sortByAndOrder);
        Page<Category> categoryPage = categoryRepository.findAll(pageable);

        List<Category> categories =  categoryPage.getContent();
        if(categories.isEmpty()) throw new APIException("No categories are created till now");
        List<CategoryDTO> categoryDTOS = categories.stream()
                .map(c -> modelMapper.map(c, CategoryDTO.class) ).toList();

        return new CategoryResponse(categoryDTOS , pageNumber , pageSize, categoryPage.getTotalElements() , categoryPage.getSize() , categoryPage.isLast());
    }

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        Category category1 = categoryRepository.findByCategoryName(category.getCategoryName());
        if(category1 != null) throw new APIException("Category Already exists with " + category1.getCategoryName() + " name");
        Category category2 =  categoryRepository.save(category);
        return modelMapper.map(category2, CategoryDTO.class);
    }

    @Override
    public CategoryDTO deleteCategory(Long categoryId){
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if(optionalCategory.isEmpty())
            throw  new ResourceNotFoundException("Category" , "categoryId" , categoryId);

        categoryRepository.deleteById(categoryId);
        return modelMapper.map(optionalCategory.get() , CategoryDTO.class);
    }

    @Override
    public CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category existingCategory = optionalCategory.get();
            existingCategory.setCategoryName(categoryDTO.getCategoryName());
            Category category =  categoryRepository.save(existingCategory);
            return modelMapper.map(category, CategoryDTO.class);
        }
        throw  new ResourceNotFoundException("Category" , "CategoryId" , categoryId);
    }
}
