package com.example.server.controllers;

import com.example.server.pojo.BookType;
import com.example.server.repository.BookTypeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BookTypeController {

    @Autowired
    private BookTypeRepository bookTypeRepository;

    @RequestMapping("/neo4j")
    public int testNeo4j(){
        // 先删除所有的内容
        bookTypeRepository.deleteAll();
        // 添加书籍类型
        BookType bookType1 = new BookType("文学");
        BookType bookType2 = new BookType("轻文学");
        BookType bookType3 = new BookType("大学教材");
        BookType bookType4 = new BookType("名著");
        BookType bookType5 = new BookType("三秋缒");
        BookType bookType6 = new BookType("八目迷");


        // 数据准备
        bookType1.addBookID(12);
        bookType1.addBookID(13);
        bookType1.addBookID(14);
        bookType1.addBookID(15);

        bookType2.addBookID(9);
        bookType2.addBookID(10);
        bookType2.addBookID(11);
        bookType2.addBookID(12);

        bookType3.addBookID(1);

        bookType4.addBookID(2);
        bookType4.addBookID(3);

        bookType5.addBookID(4);
        bookType5.addBookID(5);
        bookType5.addBookID(6);
        bookType5.addBookID(7);

        bookType6.addBookID(8);



        bookType1.addRelateBookType(bookType2);
        bookType1.addRelateBookType(bookType3);
        bookType1.addRelateBookType(bookType4);

        bookType2.addRelateBookType(bookType5);
        bookType2.addRelateBookType(bookType6);
        bookType3.addRelateBookType(bookType5);
        bookType3.addRelateBookType(bookType6);
        bookType4.addRelateBookType(bookType5);
        bookType4.addRelateBookType(bookType6);




        bookTypeRepository.save(bookType1);
        bookTypeRepository.save(bookType2);
        bookTypeRepository.save(bookType3);
        bookTypeRepository.save(bookType4);
        bookTypeRepository.save(bookType5);
        bookTypeRepository.save(bookType6);


        return 1;
    }

}
