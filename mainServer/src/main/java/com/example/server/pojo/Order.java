package com.example.server.pojo;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("order_table")
public class Order {
    public Integer id;
    public String username;
    public String address;
    public String phone;
    public String orderstate;
}
