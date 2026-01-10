package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.model.Product;
import com.ecommerce.Ecommerce.payload.ProductDTO;
import com.ecommerce.Ecommerce.payload.ProductResponse;
import com.ecommerce.Ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/catagories/{categoryId}/product")
    public ResponseEntity<ProductDTO> createProduct(
            @RequestBody Product product,
            @PathVariable Long categoryId
    ){
        ProductDTO productDTO1 = productService.createProduct(product , categoryId);
        return new ResponseEntity<>(productDTO1 , HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(){
        return productService.getAllProducts();
    }

    @GetMapping("/public/catagories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId){
        return new ResponseEntity<>(productService.getProductsByCategory(categoryId) , HttpStatus.OK);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword){
        return new ResponseEntity<>(productService.getProductsByKeyword(keyword) , HttpStatus.FOUND);
    }

}
