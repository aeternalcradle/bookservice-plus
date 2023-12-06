package com.example.server.vo;

public class Result <T> {
    public int code;
    public String msg;
    public T data;

    public static <T> Result success() {
    Result r = new Result();
    r.msg="suc";
    r.code=0;
    return r;
    }
    public static <T> Result success(T data) {
        Result r = new Result();
        r.msg="suc";
        r.code=0;
        r.data=data;
        return r;
    }
    public static <T> Result fail() {
        Result r = new Result();
        r.msg="fail";
        r.code=1;

        return r;
    }
}