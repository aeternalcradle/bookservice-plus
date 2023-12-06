package com.example.server.vo;

import lombok.Data;

@Data
public class PoiVo {
    public Integer id;
    public Integer key;
    public String name;
    public Float price;
    public Integer num;
    public Integer bookId;
    public String coverUrl;

}
