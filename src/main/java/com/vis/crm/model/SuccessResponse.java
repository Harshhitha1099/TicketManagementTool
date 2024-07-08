package com.vis.crm.model;


import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class SuccessResponse<T> extends AResponse {

    private T result;

    public SuccessResponse() {
        super(true);
    }

    public SuccessResponse(String message) {
        super(true, message);
    }

    public SuccessResponse(T result, String message) {
        super(true, message);
        this.result = result;
    }

}

