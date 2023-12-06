package com.example.server.pojo;

import org.springframework.data.annotation.Id;

public class Person {

    @Id
    private String id;
    private String username;
    private String iconBase64;

    public Person(String id, String username, String iconBase64){
        this.id = id;
        this.username = username;
        this.iconBase64 = iconBase64;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIconBase64() {
        return iconBase64;
    }

    public void setIconBase64(String iconBase64) {
        this.iconBase64 = iconBase64;
    }
}
