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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private FileService fileService;

    @Value("${product.image}")
    private String path;

    @Override
    public ProductDTO createProduct(ProductDTO productDTO, Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryId", categoryId)
        );
        var product= modelMapper.map(productDTO , Product.class);
        product.setCategory(category);
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        product.setSpecialPrice(specialPrice);
        product.setImage("product.png");
        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sortAndOrderBy = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable page =  PageRequest.of(pageNumber , pageSize , sortAndOrderBy);

        Page<Product> productsPage = productRepository.findAll(page);
        List<Product> products = productsPage.getContent();
        List<ProductDTO> productDTOs = products.stream().map(product->
           modelMapper.map(product , ProductDTO.class)
        ).toList();
        return new ProductResponse(productDTOs ,pageNumber , pageSize , productsPage.getTotalElements() , productsPage.getTotalPages() , productsPage.isLast());
    }


    @Override
    public ProductResponse getProductsByCategory(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() ->
                new ResourceNotFoundException("Category", "categoryId", categoryId)
        );

        Sort sortAndOrderBy = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable page =  PageRequest.of(pageNumber , pageSize , sortAndOrderBy);

        Page<Product> productsPage = productRepository.findByCategoryOrderByCategoryAsc(category, page);
        List<Product> products = productsPage.getContent();

        var productDTOs = products.stream().map(product -> modelMapper.map(product , ProductDTO.class)).toList();
        return new ProductResponse(productDTOs ,pageNumber , pageSize , productsPage.getTotalElements() , productsPage.getTotalPages() , productsPage.isLast());

    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

        Sort sortAndOrderBy = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable page =  PageRequest.of(pageNumber , pageSize , sortAndOrderBy);

        Page<Product> productsPage = productRepository.findByProductNameLikeIgnoreCase('%' + keyword + '%', page);
        List<Product> products = productsPage.getContent();

        var productDTOs = products.stream().map(product -> modelMapper.map(product , ProductDTO.class)).toList();
        return new ProductResponse(productDTOs ,pageNumber , pageSize , productsPage.getTotalElements() , productsPage.getTotalPages() , productsPage.isLast());

    }


    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO product) {
        var productFromDb = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product" , "productId" , productId));
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setDiscount(product.getDiscount());
        productFromDb.setQuantity(product.getQuantity());
        double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
        productFromDb.setSpecialPrice(specialPrice);
        var savedProduct = productRepository.save(productFromDb);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }


    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product" , "productId" , productId));
        productRepository.deleteById(productId);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDb = productRepository.findById(productId).orElseThrow(()-> new ResourceNotFoundException("Product", "productId" , productId));
        String uploadedFileName =  fileService.uploadFile(path, image);
        productFromDb.setImage(uploadedFileName);
        Product savedProduct = productRepository.save(productFromDb);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }



}
