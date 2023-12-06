package com.example.server.controllers;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.exception.PoiException;
import com.example.server.pojo.Book;
import com.example.server.pojo.User;
import com.example.server.service.IUserService;
import com.example.server.service.Impl.UserServiceImpl;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    //spring依赖注入，它用于告诉Spring容器在需要使用UserServiceImpl对象时，将其自动注入到UserService字段中
    @Autowired
    private IUserService userService;

    @GetMapping("list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "30") int pageSize){
        log.info("my info,pageNum={} pageSize={}",pageNum,pageSize);
        Page<User> page = new Page<User>(pageNum,pageSize);
        IPage<User> pageResult = userService.page(page);
        List voList = pageResult.getRecords().stream().map(User->{

            return User;
        }).collect(Collectors.toList());

        pageResult.setRecords(voList);

        return Result.success(pageResult);
    }

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable int id){
        log.info("my detail,id={}",id);

        User user = userService.getById(id);
        if(user==null){
            throw  PoiException.NotFound();
        }
        return Result.success(user);
    }

    @PostMapping("add")
    public Result add(@RequestBody User user){
        log.info("book add,name = {} ",user.username);
        userService.save(user);
        return Result.success(user);
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        log.info("my detail,id={}",id);
        userService.removeById(id);
        return Result.success();
    }

    @PutMapping("edit/{id}")
    public Result edit(@RequestBody User user , @PathVariable int id){
        user.setId(id);
        userService.updateById(user);
        return Result.success(user);
    }


}

