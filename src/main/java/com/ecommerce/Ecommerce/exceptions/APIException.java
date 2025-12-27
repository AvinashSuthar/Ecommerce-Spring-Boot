package com.ecommerce.Ecommerce.exceptions;

public class APIException extends  RuntimeException{
    String message;

    public APIException(String message) {
        super(message);
    }

    public APIException() {
    }
}
