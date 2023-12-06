package com.example.server.controllers;

import com.example.server.pojo.Book;
import com.example.server.pojo.Person;
import com.example.server.repository.PersonRepository;
import com.example.server.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @GetMapping("getImage/{username}")
    public Result getImage(@PathVariable String username) {
        Person person = personRepository.findByUsername(username);
        return Result.success(person.getIconBase64());
    }

    @PostMapping("add")
    public Result add(@RequestBody Person person){
        personRepository.save(person);
        return Result.success(person);
    }

}
