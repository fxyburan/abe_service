package com.vontroy.common.mybatis.domain;

import java.sql.Date;

public class User {
    private int id;
    private String name;
    private Date createTime;

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String toString() {
        return "id = " + id + ", name = " + name + ", createTime = " + createTime;
    }
}
