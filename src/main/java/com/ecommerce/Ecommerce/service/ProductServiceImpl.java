package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.Ecommerce.model.Category;
import com.ecommerce.Ecommerce.model.Product;
import com.ecommerce.Ecommerce.payload.ProductDTO;
import com.ecommerce.Ecommerce.payload.ProductResponse;
import com.ecommerce.Ecommerce.respositories.CategoryRepository;
import com.ecommerce.Ecommerce.respositories.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO createProduct(Product product, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryId", categoryId)
        );
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        product.setImage("product.png");
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ResponseEntity<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        List<ProductDTO> productDTOs = products.stream().map(product->
           modelMapper.map(product , ProductDTO.class)
        ).toList();
        ProductResponse productResponse = new ProductResponse(productDTOs);
        return new ResponseEntity<>(productResponse , HttpStatus.OK);
    }


    @Override
    public ProductResponse getProductsByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryId", categoryId)
        );
        var products = productRepository.findByCategoryOrderByCategoryAsc(category);
        var productDTOs = products.stream().map(product -> modelMapper.map(product , ProductDTO.class)).toList();
        return new ProductResponse(productDTOs);
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {

        var products = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%');
        var productDTOs = products.stream().map(product -> modelMapper.map(product , ProductDTO.class)).toList();
        return new ProductResponse(productDTOs);
    }


}
