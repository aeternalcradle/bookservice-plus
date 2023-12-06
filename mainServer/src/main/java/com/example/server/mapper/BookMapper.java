package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.server.pojo.Book;
//          数据对象访问层:
//      	负责与数据库进行交互，执行数据的持久化和访问操作。
//        	提供对数据库的增删改查等操作方法。
//        	与数据库技术和查询语言进行交互，例如使用 MyBatis-Plus 进行数据库的交流

public interface BookMapper extends BaseMapper<Book> {

}
