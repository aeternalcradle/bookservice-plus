package com.example.server.repository;

import com.example.server.pojo.Person;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;


public interface PersonRepository extends MongoRepository<Person, String> {

    Person findByUsername(@Param("name") String name);

}
