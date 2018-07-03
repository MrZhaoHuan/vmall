package com.zhao.freemarker;

import java.util.Date;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-28 21:26
 * @描述
 */
public class User {
    private long id;
    private String name;
    private double money;
    private Date createTime;

    public User(long id, String name, double money, Date createTime) {
        this.id = id;
        this.name = name;
        this.money = money;
        this.createTime = createTime;
    }

    public User(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
