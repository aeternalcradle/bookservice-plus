package com.example.server.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("userauth_table")
public class UserAuth {
    public Integer id;
    public Integer pid;
    public String username;
    public String email;
    public String password;
    public String managerPassword;
    public Integer banId;
    public Integer isManager;
}
