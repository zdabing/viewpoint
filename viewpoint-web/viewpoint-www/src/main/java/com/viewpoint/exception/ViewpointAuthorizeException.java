package com.viewpoint.exception;


import lombok.Data;

@Data
public class ViewpointAuthorizeException extends RuntimeException {

    public ViewpointAuthorizeException(String message) {
        super(message);
    }
}
