package com.liaozl.demo.mongo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EmployeeMongoDao extends MongoRepository<com.liaozl.demo.mongo.dao.entity.Employee, Integer> {

}
