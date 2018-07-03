package com.zhao.util;

import lombok.Getter;

import java.io.Serializable;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-20 19:03
 * @描述  easyui异步tree-数据格式
 * [{
        "id": 1,
        "text": "Node 1",
        "state": "closed"
        }
    ]
 */
@Getter
public class EasyuiTree implements Serializable{
    private static  final long serialVersionUID  =1L;
    private long id;
    private String text;
    private String state;

    public static EasyuiTree buid(){
        return  new EasyuiTree();
    }

    public EasyuiTree setId(long id) {
        this.id = id;
        return  this;
    }

    public EasyuiTree setState(String state) {
        this.state = state;
        return this;
    }

    public EasyuiTree setText(String text){
        this.text = text;
        return this;
    }
}
