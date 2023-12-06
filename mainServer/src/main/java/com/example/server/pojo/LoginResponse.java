package com.example.server.pojo;

public class LoginResponse {
    private int status;
    private Integer userId;

    public LoginResponse(int status, Integer userId) {
        this.status = status;
        this.userId = userId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}
