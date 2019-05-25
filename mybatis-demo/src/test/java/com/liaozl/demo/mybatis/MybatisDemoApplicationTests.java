package com.liaozl.demo.mybatis;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.mybatis.dao.entity.Company;
import com.liaozl.demo.mybatis.dao.mapper.CompanyMapper;
import com.liaozl.demo.mybatis.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MybatisDemoApplicationTests {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private CompanyService companyService;

    @Test
    public void testQuery() {
        List<Company> companyList = companyMapper.selectAll();
        log.info("companyList:{}", JSON.toJSONString(companyList));
    }

    @Test
    public void testDelCompany() {
       Company company = companyMapper.selectByPrimaryKey(1);
       log.info("company:{}", JSON.toJSONString(company));

       try{
           companyService.delCompany(1);
       }catch (Exception e) {
           log.error("delCompany error: ", e);
       }
    }

}
