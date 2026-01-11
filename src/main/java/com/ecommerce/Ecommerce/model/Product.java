package com.ecommerce.Ecommerce.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long productId;

    @NotEmpty(message =  "Product name should not be empty")
    @Size(min = 3 , message = "Product name should be greater than 3 characters")
    private String productName;

    @NotEmpty(message =  "Description name should not be empty")
    @Size(min = 3 , message = "Description should be greater than 6 characters")
    private String description;
    private String image;

    @Min(value = 1, message = "Quantity should be greater than 1")
    private Integer quantity;
    @DecimalMin(value = "1.0", message = "Price should be greater than 1")
    private double price;
    private double discount;
    private double specialPrice;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

}
