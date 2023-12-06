package com.example.server.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("user_table")
public class User {
    public Integer id;
    public String username;
    public String email;
    public String twitter;
}
