package com.liaozl.demo.mongo.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import com.liaozl.demo.mongo.dao.entity.Company;

import java.util.List;

@Slf4j
@Service
public class CompanyMongoDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> void save(T t) {
        mongoTemplate.save(t);
    }

    public Company findById(Object id) {
        return mongoTemplate.findById(id, Company.class);
    }

    public <T> long deleteById(T t) {
        return mongoTemplate.remove(t).getDeletedCount();
    }

    public <T> List<T> getPage(int currentPage, int pageSize, Class<T> tClass) {
        Query query = new Query();
        query.skip((currentPage - 1) * pageSize);
        query.limit(pageSize);
        return mongoTemplate.find(query, tClass);
    }

    public List<Company> findAll() {
        return mongoTemplate.findAll(Company.class);
    }

    public List<Company> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is(name));
        return mongoTemplate.find(query, Company.class);
    }

    public List<Company> findByNameLike(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(name));
        return mongoTemplate.find(query, Company.class);
    }

    public long update(Company company) {
        Query query = new Query(Criteria.where("_id").is(company.getId()));

        Update update = new Update();
        update.set("name", company.getName());
        update.set("address", company.getAddress());

        return mongoTemplate.updateFirst(query, update, Company.class).getModifiedCount();
    }

}
