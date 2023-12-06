package com.example.server.exception;

public class PoiException extends RuntimeException {
    public PoiException(String msg){
        super(msg);
    }

    public static PoiException NotFound(){
        return new PoiException("Not found");
    }

    public static PoiException OperateFail(){
        return new PoiException("Operate Fail");
    }

    public static PoiException Unknown(){
        return new PoiException("Unknown error");
    }
}
