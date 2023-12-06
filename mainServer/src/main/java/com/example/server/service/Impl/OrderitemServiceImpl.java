package com.example.server.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.OrderitemMapper;
import com.example.server.pojo.Orderitem;
import com.example.server.service.IOrderitemService;
import org.springframework.stereotype.Service;

@Service
public class OrderitemServiceImpl extends ServiceImpl<OrderitemMapper, Orderitem>implements IOrderitemService {
}
