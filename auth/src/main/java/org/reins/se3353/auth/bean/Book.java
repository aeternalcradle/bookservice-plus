package org.reins.se3353.auth.bean;


import jakarta.persistence.Table;
import lombok.Data;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Data
@Entity
@Table(name="book_table")
public class Book {
    @Id
    public Integer id;
    public String name;
    public String author;
    public String description;
    public Float price;
    public String coverUrl;
    public Integer num;
    public String publisher;
}
