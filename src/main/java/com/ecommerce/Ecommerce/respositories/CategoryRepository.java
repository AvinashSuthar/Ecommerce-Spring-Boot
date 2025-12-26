package com.ecommerce.Ecommerce.respositories;

import com.ecommerce.Ecommerce.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}