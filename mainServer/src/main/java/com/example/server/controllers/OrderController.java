package com.example.server.controllers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.exception.PoiException;
import com.example.server.pojo.Order;
import com.example.server.service.IOrderService;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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
@RequestMapping("/order")
public class OrderController {
    //spring依赖注入，它用于告诉Spring容器在需要使用BookServiceImpl对象时，将其自动注入到bookService字段中
    @Autowired
    private IOrderService orderService;

    @GetMapping("list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "30") int pageSize){
        Page<Order> page = new Page<Order>(pageNum,pageSize);
        IPage<Order> pageResult = orderService.page(page);
        List voList = pageResult.getRecords().stream().map(Order->{

            return Order;
        }).collect(Collectors.toList());

        pageResult.setRecords(voList);

        return Result.success(pageResult);
    }

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable int id){

        Order Order = orderService.getById(id);
        if(Order==null){
            throw  PoiException.NotFound();
        }
        return Result.success(Order);
    }

    @PostMapping("add")
    public Result add(@RequestBody Order Order){
        orderService.save(Order);
        return Result.success(Order);
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        orderService.removeById(id);
        return Result.success();
    }
    @PutMapping("edit/{id}")
    public Result edit(@RequestBody Order Order , @PathVariable int id){
        Order.setId(id);
        orderService.updateById(Order);
        return Result.success(Order);
    }



}

