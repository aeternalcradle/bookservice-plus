package com.example.server.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("poi_table")
public class Poi {
    public Integer id;
    public Integer pid;
    public String name;
    public Float price;
    public Integer num;
    public Integer bookId;
    public String coverUrl;
    public String username;

}
