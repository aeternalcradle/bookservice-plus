package com.example.server.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.server.mapper.BookMapper;
import com.example.server.pojo.Book;
import com.example.server.service.IBookService;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book>implements IBookService {
    @Override
    public Book searchByName(String name) {
        // 调用MyBatis Plus的查询方法，根据名字查询订单
        return baseMapper.selectOne(new QueryWrapper<Book>().eq("name", name));

    }
}
