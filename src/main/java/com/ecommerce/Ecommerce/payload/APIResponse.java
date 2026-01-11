package com.ecommerce.Ecommerce.payload;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class APIResponse<T> {
    private String message;
    private boolean status;
    private T data;

    public APIResponse(String message, boolean status) {
        this.message = message;
        this.status = status;
        this.data = null;
    }

}
