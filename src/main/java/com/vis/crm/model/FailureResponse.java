package com.vis.crm.model;


import java.util.Arrays;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FailureResponse extends AResponse {

    private String exception;

    public FailureResponse() {
        super(false);
    }

    public FailureResponse(String message) {
        super(false, message);
    }

    public FailureResponse(Exception ex, String message) {
        super(false, message);
        this.exception = Arrays.toString(ex.getStackTrace());
    }

    public FailureResponse(Exception ex) {
        super(false, "Exception : "+ex.getMessage());
        this.exception = Arrays.toString(ex.getStackTrace());
    }

}
