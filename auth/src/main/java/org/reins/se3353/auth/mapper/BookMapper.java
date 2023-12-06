package org.reins.se3353.auth.mapper;

import org.reins.se3353.auth.bean.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookMapper extends JpaRepository<Book, Integer> {
    Book findBookByName(String Name);
}
