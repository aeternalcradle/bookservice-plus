package com.example.server.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("book_table")
public class Book {
    public Integer id;
    public String name;
    public String author;
    public String description;
    public Float price;
    public String coverUrl;
    public Integer num;
    public String publisher;


}
