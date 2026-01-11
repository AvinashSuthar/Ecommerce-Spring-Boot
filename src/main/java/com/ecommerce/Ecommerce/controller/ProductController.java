package com.ecommerce.Ecommerce.controller;

import com.ecommerce.Ecommerce.config.AppConstants;
import com.ecommerce.Ecommerce.payload.APIResponse;
import com.ecommerce.Ecommerce.payload.ProductDTO;
import com.ecommerce.Ecommerce.payload.ProductResponse;
import com.ecommerce.Ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/catagories/{categoryId}/product")
    public ResponseEntity<APIResponse<ProductDTO>> createProduct(
           @Valid @RequestBody ProductDTO productDTO,
            @PathVariable Long categoryId
    ){
        ProductDTO newProductDTO = productService.createProduct(productDTO , categoryId);
        return new ResponseEntity<>(new APIResponse<>("Product created successfully" , true, newProductDTO) , HttpStatus.CREATED);
    }

    @GetMapping("/public/products")
    public ResponseEntity<APIResponse<ProductResponse>> getAllProducts(
            @RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
            @RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
            @RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_PRODUCTS_BY , required = false) String sortBy,
            @RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR , required = false) String sortDir
    ){
         ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize , sortBy, sortDir);
         return new ResponseEntity<>(new APIResponse<>("Products fetched successfully" , true, productResponse) , HttpStatus.OK);
    }

    @GetMapping("/public/catagories/{categoryId}/products")
    public ResponseEntity<APIResponse<ProductResponse>> getProductsByCategory(@PathVariable Long categoryId,
      @RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
      @RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
      @RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_PRODUCTS_BY , required = false) String sortBy,
      @RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR , required = false) String sortDir
    ){
        ProductResponse productResponse = productService.getProductsByCategory(categoryId , pageNumber, pageSize , sortBy, sortDir) ;
        return new ResponseEntity<>(new APIResponse<>("Products fetched successfully" , true, productResponse) ,HttpStatus.OK);
    }
    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<APIResponse<ProductResponse>> getProductsByKeyword(@PathVariable String keyword,
    @RequestParam(value = "pageNumber" , defaultValue = AppConstants.PAGE_NUMBER , required = false) Integer pageNumber,
    @RequestParam(value = "pageSize" , defaultValue = AppConstants.PAGE_SIZE , required = false) Integer pageSize,
    @RequestParam(value = "sortBy" , defaultValue = AppConstants.SORT_PRODUCTS_BY , required = false) String sortBy,
    @RequestParam(value = "sortDir" , defaultValue = AppConstants.SORT_DIR , required = false) String sortDir){
               ProductResponse productResponse =  productService.getProductsByKeyword(keyword  , pageNumber, pageSize , sortBy, sortDir);
        return new ResponseEntity<>( new APIResponse<>("Products fetched successfully" , true, productResponse) , HttpStatus.FOUND);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<APIResponse<ProductDTO>> updateProduct(
           @Valid @RequestBody ProductDTO productDTO,
            @PathVariable Long productId
    ){
        ProductDTO updatedProductDTO = productService.updateProduct(productId, productDTO);
        return new ResponseEntity<>(new APIResponse<>("Products updated successfully" , true, updatedProductDTO) , HttpStatus.OK);
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<APIResponse<ProductDTO>> updateProductImage(
            @RequestParam("image") MultipartFile image,
            @PathVariable Long productId
    ) throws IOException {
        ProductDTO updatedProductDTO = productService.updateProductImage(productId, image);
        return new ResponseEntity<>(new APIResponse<>("Products updated successfully" , true, updatedProductDTO), HttpStatus.OK);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<APIResponse<ProductDTO>> deleteProduct(@PathVariable Long productId){
        ProductDTO deletedProduct = productService.deleteProduct(productId);
        return new ResponseEntity<>(new APIResponse<>("Products deleted successfully" , true, deletedProduct) , HttpStatus.OK);
    }
}
