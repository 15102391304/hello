package com.example.bootsecurity.common;

public class Result<T> {
    private T data;
    private String message;
    private String code;
    public static Result success(Object o){
        Result result=new Result();
        result.setCode("0000");
        result.setData(o);
        result.setMessage(null);
        return result;
    }
    public static Result  fail(Object o){
        Result result=new Result();
        result.setCode("1111");
        result.setData(o);
        result.setMessage(null);
        return result;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
