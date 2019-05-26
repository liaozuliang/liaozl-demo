package com.liaozl.demo.mongo;

import com.alibaba.fastjson.JSON;
import com.liaozl.demo.mongo.dao.CompanyMongoDao;
import com.liaozl.demo.mongo.dao.EmployeeMongoDao;
import com.liaozl.demo.mongo.dao.entity.Company;
import com.liaozl.demo.mongo.dao.entity.Employee;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoDemoApplicationTests {

    @Autowired
    private EmployeeMongoDao employeeMongoDao;

    @Autowired
    private CompanyMongoDao companyMongoDao;

    @Test
    public void init() {
        Company company = new Company();
        company.setId(1);
        company.setName("全民吃瓜");
        company.setAddress("南山蛇口");
        company.setCreateTime(new Date());
        companyMongoDao.save(company);

        company = new Company();
        company.setId(2);
        company.setName("全民吃瓜2");
        company.setAddress("南山蛇口");
        company.setCreateTime(new Date());
        companyMongoDao.save(company);

        company = new Company();
        company.setId(3);
        company.setName("你我金融");
        company.setAddress("南山科技园北区");
        company.setCreateTime(new Date());
        companyMongoDao.save(company);

        Employee employee = new Employee();
        employee.setId(1);
        employee.setCompanyId(1);
        employee.setAge(11);
        employee.setName("我是员工1");
        employee.setSex((byte) 1);
        employee.setCreateTime(new Date());
        employeeMongoDao.insert(employee);

        employee = new Employee();
        employee.setId(2);
        employee.setCompanyId(2);
        employee.setAge(22);
        employee.setName("我是员工2");
        employee.setSex((byte) 1);
        employee.setCreateTime(new Date());
        employeeMongoDao.insert(employee);

        employee = new Employee();
        employee.setId(3);
        employee.setCompanyId(1);
        employee.setAge(33);
        employee.setName("我是员工3");
        employee.setSex((byte) 1);
        employee.setCreateTime(new Date());
        employeeMongoDao.insert(employee);

        List<Company> companyList = companyMongoDao.findAll();
        log.info("companyList:{}", JSON.toJSONString(companyList));

        List<Employee> employeeList = employeeMongoDao.findAll();
        log.info("employeeList:{}", JSON.toJSONString(employeeList));
    }

    @Test
    public void testMongoBaseDao() {
        Optional<Employee> employee = employeeMongoDao.findById(1);
        if (employee.isPresent()) {
            log.info("old employee:{}", JSON.toJSONString(employee.get()));
        }

        Employee newEmployee = employee.get();
        newEmployee.setName("我的名字被改了");
        employeeMongoDao.save(newEmployee);

        employee = employeeMongoDao.findById(1);
        if (employee.isPresent()) {
            log.info("new employee:{}", JSON.toJSONString(employee.get()));
        }
    }

    @Test
    public void testCompanyMongoDao() {
        List<Company> companyList = companyMongoDao.findByName("全民吃瓜");
        log.info("companyList:{}", JSON.toJSONString(companyList));

        List<Company> companyList2 = companyMongoDao.findByNameLike("全民吃瓜");
        log.info("companyList2:{}", JSON.toJSONString(companyList2));

        Company company = companyMongoDao.findById(3);
        log.info("old company:{}", JSON.toJSONString(company));

        company.setName("我是公司，我的名字被修改过了");
        company.setAddress("我的地址也被修改过了");
        companyMongoDao.save(company);

        company = companyMongoDao.findById(3);
        log.info("new company:{}", JSON.toJSONString(company));
    }

    @Test
    public void test3() {
        List<Employee> employeeList = employeeMongoDao.findByNameStartingWith("我是员工");
        log.info("employeeList:{}", JSON.toJSONString(employeeList));

        List<Employee> employeeList2 = employeeMongoDao.findBySexAndAge((byte) 1, 11);
        log.info("employeeList2:{}", JSON.toJSONString(employeeList2));

        List<Employee> employeeList3 = employeeMongoDao.findBySex((byte) 1);
        log.info("employeeList3:{}", JSON.toJSONString(employeeList3));

        List<Employee> employeeList4 = employeeMongoDao.findByAgeBetween2(33, 50);
        log.info("employeeList4:{}", JSON.toJSONString(employeeList4));

        List<Employee> employeeList5 = employeeMongoDao.findByAgeBetween1To25();
        log.info("employeeList5:{}", JSON.toJSONString(employeeList5));
    }
}
