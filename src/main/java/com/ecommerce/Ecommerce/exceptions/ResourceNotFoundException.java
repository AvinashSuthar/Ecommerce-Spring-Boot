package com.ecommerce.Ecommerce.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private String field;
    private Long fieldId;

    public ResourceNotFoundException() {

    }

    public ResourceNotFoundException(String resourceName, String fieldName, String field) {
        super(String.format("%s not found with %s : %s" , resourceName, field , fieldName));
    }
    public ResourceNotFoundException(String resourceName, String field, Long fieldId) {
        super(String.format("%s not found with %s : %d" , resourceName, field , fieldId));
    }
}
