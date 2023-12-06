package com.example.server.controllers;
import com.example.server.pojo.Book;
import com.example.server.service.IBookService;
import com.example.server.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphQLController {
    @Autowired
    private IBookService bookService;
    @QueryMapping
    public Book bookByName(@Argument String name) {
        return bookService.searchByName(name);
    }

}
