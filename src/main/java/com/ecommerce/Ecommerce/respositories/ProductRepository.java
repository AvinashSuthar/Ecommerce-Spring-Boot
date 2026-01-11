package com.ecommerce.Ecommerce.respositories;

import com.ecommerce.Ecommerce.model.Category;
import com.ecommerce.Ecommerce.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByCategoryOrderByCategoryAsc(Category category, Pageable page);

    Page<Product> findByProductNameLikeIgnoreCase(String keyword, Pageable page);
}
