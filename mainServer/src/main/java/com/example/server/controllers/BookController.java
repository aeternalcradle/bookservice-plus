package com.example.server.controllers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.exception.PoiException;
import com.example.server.pojo.BookType;
import com.example.server.repository.BookTypeRepository;
import com.example.server.service.IBookService;
import com.example.server.pojo.Book;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

//          Controller作用:
//      	负责接收客户端请求并处理请求的分发和响应。
//          提供接口给前端或其他系统进行交互。
//         	处理请求参数的验证和转换。
//        	协调调用服务层的方法来完成业务逻辑的处理。
//        	返回响应给客户端或其他系统。

@RestController
@Slf4j
@RequestMapping("/book")
public class BookController {
    //spring依赖注入，它用于告诉Spring容器在需要使用BookServiceImpl对象时，将其自动注入到bookService字段中
    @Autowired
    private IBookService bookService;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private BookTypeRepository bookTypeRepository;

    @GetMapping("list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "30") int pageSize){
        Page<Book> page = new Page<Book>(pageNum,pageSize);
        IPage<Book> pageResult = bookService.page(page);
        List voList = pageResult.getRecords().stream().map(book->{

            return book;
        }).collect(Collectors.toList());

        pageResult.setRecords(voList);

        return Result.success(pageResult);
    }

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable int id) {
        try{
    // 先尝试从缓存中获取
        String cacheKey = "book_" + id;
        Book book = (Book) redisTemplate.opsForValue().get(cacheKey);
        log.info("从redis中获取书籍");
        log.info(String.valueOf(book));
        if (book == null) {
            // 如果缓存中没有，从数据库中查询
            book = bookService.getById(id);
            log.info("从数据库中获取书籍");
            if (book == null) {
                throw PoiException.NotFound();
            }
            // 将查询结果写入缓存
            redisTemplate.opsForValue().set(cacheKey, book);
        }

        return Result.success(book);}
        catch(Exception e){
            log.error("Error retrieving book from Redis or database: {}", e.getMessage());

            Book book = bookService.getById(id);
            if (book == null) {
                throw PoiException.NotFound();
            }

            return Result.success(book);
        }
    }


    @GetMapping("getBookByTags/{tagName}")
    public Result getBookByTags(@PathVariable String tagName){
        // 先根据标签的名字取找对应的节点
        List<BookType> list0 = bookTypeRepository.findBookTypesByTypeNameLike(tagName);
        HashMap<Integer, Integer> result = new HashMap<>();
        List<Book> resultBook = new ArrayList<>();

        // 对于上面找到的节点，把所有相关的BookID放入HashSet
        for (BookType bookType : list0) {
            for (int j = 0; j < bookType.getBookIDs().size(); j++) {
                int id = bookType.getBookIDs().get(j);
                result.put(id, 1);
            }
        }

        // 再查找一跳之内的list1保存，两跳的用list2保存，然后手动合并
        for (BookType type : list0) {
            String keyName = type.getTypeName();
            List<BookType> list1 = bookTypeRepository.findNodeRelatedBookTypesDistance1(keyName);
            List<BookType> list2 = bookTypeRepository.findNodeRelatedBookTypesDistance2(keyName);

            for (BookType bookType : list1) {
                for (int j = 0; j < bookType.getBookIDs().size(); j++) {
                    int id = bookType.getBookIDs().get(j);
                    result.put(id, 1);
                }
            }

            for (BookType bookType : list2) {
                for (int j = 0; j < bookType.getBookIDs().size(); j++) {
                    int id = bookType.getBookIDs().get(j);
                    result.put(id, 1);
                }
            }
        }
        for(int id: result.keySet()){
            resultBook.add(bookService.getById(id));
        }

        return Result.success(resultBook);
    }



    @Transactional
    @PostMapping("add")
    public Result add(@RequestBody Book book){
        String cacheKey = "book_" + book.id;
        redisTemplate.opsForValue().set(cacheKey, book);
        bookService.save(book);
        return Result.success(book);
    }



    @Transactional
    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        String cacheKey = "book_" + id;
        redisTemplate.opsForValue().getAndDelete(cacheKey);

        bookService.removeById(id);
        return Result.success();
    }

    @Transactional
    @PutMapping("edit/{id}")
    public Result edit(@RequestBody Book book , @PathVariable int id){
        book.setId(id);
        String cacheKey = "book_" + book.id;
        bookService.updateById(book);
        redisTemplate.opsForValue().set(cacheKey, book);
        return Result.success(book);
    }

    @PutMapping("editnum/{id}")
    public Result editNum(@RequestBody Book book , @PathVariable int id){
        Book book1 = bookService.getById(id);
        book.setId(id);
        book.num=book1.num-book.num;
        bookService.updateById(book);
        return Result.success(book);
    }
}

