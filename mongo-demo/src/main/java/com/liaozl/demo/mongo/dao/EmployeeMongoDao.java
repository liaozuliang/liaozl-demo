package com.liaozl.demo.mongo.dao;

import com.liaozl.demo.mongo.dao.entity.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface EmployeeMongoDao extends MongoRepository<com.liaozl.demo.mongo.dao.entity.Employee, Integer> {

    List<Employee> findByNameStartingWith(String name);

    List<Employee> findBySexAndAge(byte sex, int age);

    List<Employee> findBySex(byte sex);

    @Query("{\"age\":{\"$gte\":?0,\"$lte\":?1}}")
    List<Employee> findByAgeBetween2(int minAge, int maxAge);

    @Query("{\"age\":{\"$gte\":1,\"$lte\":25}}")
    List<Employee> findByAgeBetween1To25();
}
