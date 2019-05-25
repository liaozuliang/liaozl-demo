package com.liaozl.demo.mybatis.dao.entity;

import javax.persistence.*;
import java.util.Date;

@Table(name = "t_employee")
public class Employee {
    /**
     * 员工ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 员工名称
     */
    private String name;

    /**
     * 员工性别(0:女 1:男)
     */
    private Byte sex;

    /**
     * 员工年龄
     */
    private Integer age;

    /**
     * 所属公司
     */
    @Column(name = "company_id")
    private Integer companyId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 获取员工ID
     *
     * @return id - 员工ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置员工ID
     *
     * @param id 员工ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取员工名称
     *
     * @return name - 员工名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置员工名称
     *
     * @param name 员工名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取员工性别(0:女 1:男)
     *
     * @return sex - 员工性别(0:女 1:男)
     */
    public Byte getSex() {
        return sex;
    }

    /**
     * 设置员工性别(0:女 1:男)
     *
     * @param sex 员工性别(0:女 1:男)
     */
    public void setSex(Byte sex) {
        this.sex = sex;
    }

    /**
     * 获取员工年龄
     *
     * @return age - 员工年龄
     */
    public Integer getAge() {
        return age;
    }

    /**
     * 设置员工年龄
     *
     * @param age 员工年龄
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * 获取所属公司
     *
     * @return company_id - 所属公司
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 设置所属公司
     *
     * @param companyId 所属公司
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}