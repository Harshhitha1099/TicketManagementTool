package com.vis.crm.model;


import lombok.Data;

@Data
public abstract class AResponse {

    private Boolean isSuccess;
    private String message;

    public AResponse() {

    }

    public AResponse(Boolean isSuccess){
        this.isSuccess = isSuccess;
    }

    public AResponse(Boolean isSuccess, String message){
        this.isSuccess = isSuccess;
        this.message = message;
    }
}
