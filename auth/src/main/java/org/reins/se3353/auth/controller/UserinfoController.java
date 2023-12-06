package org.reins.se3353.auth.controller;

import org.reins.se3353.auth.bean.Book;
import org.reins.se3353.auth.service.BookService;
import org.reins.se3353.auth.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserinfoController {

    @Autowired
    BookService bookService;

    @ResponseBody
    @GetMapping(value = "/getBookAuthorByName/{bookName}")
    public Response getBookAuthorByName(@PathVariable("bookName") String bookName){
        // Call the service to get the book information by name
        Book book = bookService.getBookByName(bookName);

        if (book != null) {
            // If the book is found, create a response
            Map<String, Object> data = new HashMap<>();
            data.put("bookName", book.getName());
            data.put("bookAuthor", book.getAuthor());

            return new Response("true", "Book author retrieved successfully", data);
        } else {
            // If the book is not found, return an error response
            return new Response("false", "Book not found", null);
        }
    }
    }

