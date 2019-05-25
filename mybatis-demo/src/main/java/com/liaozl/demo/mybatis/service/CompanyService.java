package com.liaozl.demo.mybatis.service;

import com.liaozl.demo.mybatis.dao.entity.Employee;
import com.liaozl.demo.mybatis.dao.mapper.CompanyMapper;
import com.liaozl.demo.mybatis.dao.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CompanyService {

    @Autowired
    private CompanyMapper companyMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Transactional
    public void delCompany(int companyId) {
        int delCompanyCount = companyMapper.deleteByPrimaryKey(companyId);

        Employee employee = new Employee();
        employee.setCompanyId(companyId);
        int employeeCount = employeeMapper.selectCount(employee);

        if (employeeCount > 0) {
            log.error("有不能删除公司[companyId={}], 该公司还有{}个员工", companyId, employeeCount);
            throw new RuntimeException("有不能删除公司[companyId=" + companyId + "], 该公司还有" + employeeCount + "个员工");
        }

        log.info("删除公司[companyId={}]成功", companyId);
    }
}
