package org.reins.se3353.auth.service;


import org.reins.se3353.auth.bean.Book;
import org.reins.se3353.auth.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;





@Service
public class BookService {
    @Autowired
    BookMapper bookMapper;

    public Book getBookByName(String bookName) {
        // You can call the BookMapper to retrieve the book by name from the database
        return bookMapper.findBookByName(bookName);
    }

}
