package com.example.bootsecurity.common.exception;

public class ServiceException extends RuntimeException {
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    private String message;
    public ServiceException(String message){
        this.message=message;
    }
}
