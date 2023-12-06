package com.example.server.controllers;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.server.exception.PoiException;
import com.example.server.pojo.Book;
import com.example.server.pojo.LoginResponse;
import com.example.server.pojo.UserAuth;
import com.example.server.service.ITimerService;
import com.example.server.service.IUserAuthService;
import com.example.server.service.Impl.UserAuthServiceImpl;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@Slf4j
@RequestMapping("/userauth")
public class UserAuthController {
    //spring依赖注入，它用于告诉Spring容器在需要使用UserAuthServiceImpl对象时，将其自动注入到UserAuthService字段中
    @Autowired
    private IUserAuthService userAuthService;

    @Autowired
    private ITimerService timerService;

    @GetMapping("list")
    public Result list(@RequestParam(defaultValue = "1") int pageNum,@RequestParam(defaultValue = "30") int pageSize){
        log.info("my info,pageNum={} pageSize={}",pageNum,pageSize);
        Page<UserAuth> page = new Page<UserAuth>(pageNum,pageSize);
        IPage<UserAuth> pageResult = userAuthService.page(page);
        List voList = pageResult.getRecords().stream().map(User->{
            return User;
        }).collect(Collectors.toList());

        pageResult.setRecords(voList);

        return Result.success(pageResult);
    }

    @GetMapping("detail/{id}")
    public Result detail(@PathVariable int id){
        log.info("my detail,id={}",id);

        UserAuth userAuth = userAuthService.getById(id);
        if(userAuth==null){
            throw  PoiException.NotFound();
        }
        return Result.success(userAuth);
    }


    @PostMapping("login")
    public int login(@RequestBody UserAuth userAuth){
        log.info("name = {} ",userAuth.username);
        UserAuth user = userAuthService.getBaseMapper().selectOne(new QueryWrapper<UserAuth>().eq("username", userAuth.username));
        if(user==null) {
            log.info("未输入用户名");
            return 0;
        }
        else if(Objects.equals(user.banId, 1) ){
            log.info("这个人被ban了");
            return 2;
        }
        else if(Objects.equals(user.password, userAuth.password)) {
            long x=timerService.startTimer(); // 初始化计时器
            log.info(String.valueOf(x));
            if(user.isManager==1)
            {
                return 3;
            }
            else return 1;
        }
        else{ log.info("用户名或者密码错误");
            return 0;
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        long sessionDuration = timerService.stopTimer(); // 停止计时器并获取会话保持时间
        log.info(String.valueOf(sessionDuration));
        return ResponseEntity.ok("Logout successful. Session duration: " + sessionDuration + " seconds");
    }

    @PostMapping("register")
    public Integer register(@RequestBody UserAuth userAuth){
        log.info("name = {} ",userAuth.username);
        UserAuth user = userAuthService.getBaseMapper().selectOne(new QueryWrapper<UserAuth>().eq("username", userAuth.username));
        if(Objects.equals(String.valueOf(user), "null")){
            return 1;
        }
        else return 0;
    }

    @PostMapping("add")
    public Result add(@RequestBody UserAuth userAuth){
        log.info("book add,name = {} ",userAuth.username);
        userAuthService.save(userAuth);
        return Result.success(userAuth);
    }

    @DeleteMapping("delete/{id}")
    public Result delete(@PathVariable int id){
        log.info("my detail,id={}",id);
        userAuthService.removeById(id);
        return Result.success();
    }

    @PutMapping("edit/{id}")
    public Result edit(@RequestBody UserAuth userAuth , @PathVariable int id){
        userAuth.setId(id);
        userAuthService.updateById(userAuth);
        return Result.success(userAuth);
    }


}

