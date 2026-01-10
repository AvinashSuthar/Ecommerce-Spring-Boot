package com.ecommerce.Ecommerce.service;

import com.ecommerce.Ecommerce.model.Product;
import com.ecommerce.Ecommerce.payload.ProductDTO;
import com.ecommerce.Ecommerce.payload.ProductResponse;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ProductDTO createProduct(Product product , Long categoryId);

    ResponseEntity<ProductResponse> getAllProducts();

    ProductResponse getProductsByCategory(Long categoryId);

    ProductResponse getProductsByKeyword(String keyword);
}
