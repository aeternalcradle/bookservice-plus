package com.example.server.controllers;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.exception.PoiException;
import com.example.server.pojo.Book;
import com.example.server.pojo.Order;
import com.example.server.pojo.Orderitem;
import com.example.server.pojo.Poi;
import com.example.server.service.IBookService;
import com.example.server.service.IOrderService;
import com.example.server.service.IOrderitemService;
import com.example.server.service.IPoiService;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/orderitem")
public class OrderitemController {
    //spring依赖注入，它用于告诉Spring容器在需要使用OrderServiceImpl对象时，将其自动注入到OrderService字段中
    @Autowired
    private IOrderitemService orderitemService;
    @Autowired
    private IBookService bookService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IPoiService poiService;

    private final KafkaTemplate<String, Orderitem> kafkaTemplate;

    public OrderitemController(KafkaTemplate<String, Orderitem> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    @GetMapping("list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "30") int pageSize){
        log.info("my info,pageNum={} pageSize={}",pageNum,pageSize);
        Page<Orderitem> page = new Page<Orderitem>(pageNum,pageSize);
        IPage<Orderitem> pageResult = orderitemService.page(page);
        List voList = pageResult.getRecords().stream().map(order->{

            return order;
        }).collect(Collectors.toList());

        pageResult.setRecords(voList);

        return Result.success(pageResult);
    }

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable int id){
        log.info("my detail,id={}",id);
        Orderitem book = orderitemService.getById(id);
        if(book==null){
            throw  PoiException.NotFound();
        }
        return Result.success(book);
    }

    @PostMapping("add")
    public Result add(@RequestBody Orderitem orderitem){
        try {
            // 发送订单数据到Kafka Topic
            kafkaTemplate.send("order-request-topic", orderitem);

            return Result.success(orderitem);
        } catch (Exception e) {
            log.error("Error adding order", e);
            return Result.fail();
        }
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        log.info("my detail,id={}",id);
        orderitemService.removeById(id);
        return Result.success();
    }


    @PutMapping("edit/{id}")
    public Result edit(@RequestBody Orderitem orderitem, @PathVariable int id){
        orderitem.setId(id);
        orderitemService.updateById(orderitem);
        return Result.success(orderitem);
    }

    @PostMapping("order-set")
    public Result orderSet(@RequestBody List<Orderitem> Orders){
        log.info(Orders.toString());
        orderMake(Orders);
        return Result.success(Orders);
    }

    @PostMapping("order-one-set")
    public Result orderOneSet(@RequestBody Orderitem Order){
        log.info(Order.toString());
        orderOneMake(Order);
        return Result.success(Order);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class, isolation = Isolation.READ_COMMITTED)
     public void orderMake(List<Orderitem> Orders){

        //先减去书籍的数目
        for (Orderitem order : Orders) {
            int bookId = order.bookId;
            int quantityToReduce = order.num;
            Book book1 = bookService.getById(bookId);
            book1.num = book1.num - quantityToReduce;
            bookService.updateById(book1);
        }

        //删除购物车中的数据
        List<Poi> poisToDelete = poiService.list(new QueryWrapper<Poi>().eq("username", Orders.get(0).username));
            List<Integer> idsToDelete = poisToDelete.stream().map(Poi::getId).collect(Collectors.toList());
            poiService.removeByIds(idsToDelete);


        //Order中添加
        Order order = new Order();
        order.id=Orders.get(0).orderId;
        order.username=Orders.get(0).username;
        order.orderstate="待发货";
        log.info(String.valueOf(order));
        orderService.save(order);

        //Orderitem中添加
        for (Orderitem orderitem : Orders) {
            add(orderitem);
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor=Exception.class, isolation = Isolation.READ_COMMITTED)
    public void orderOneMake(Orderitem order){

        //先减去书籍的数目
        int bookId = order.bookId;
        int quantityToReduce = order.num;
        Book book1 = bookService.getById(bookId);
        book1.num = book1.num - quantityToReduce;
        bookService.updateById(book1);

        //Order中添加
        Order order1 = new Order();
        order1.id=order.orderId;
        order1.username=order.username;
        order1.orderstate="待发货";
        log.info(String.valueOf(order1));
        orderService.save(order1);

        //Orderitem中添加
            add(order);

    }

}

