package com.example.server.service;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.server.pojo.Book;

//          服务层作用
//      	封装业务逻辑，提供高层次的服务和功能。
//        	调用数据访问对象层的方法来进行数据访问和操作。
//        	处理业务逻辑的处理、事务管理、数据校验等。



public interface IBookService extends IService<Book> {
    Book searchByName(String name);
}
