package com.zhao.util;

import java.io.Serializable;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-12 22:40
 * @描述  返回到客户端的数据格式
 */
public class JsonObj implements Serializable{
    private static final long serialVersionUID = 1L;
    private int status;
    private String msg;
    private Object data;
    private static  final  int successStatus = 200;
    private static  final  int errorStatus = 500;

    public JsonObj setStatus(int status) {
        this.status = status;
        return  this;
    }

    public JsonObj setMsg(String msg) {
        this.msg = msg;
        return  this;
    }

    public JsonObj setData(Object data) {
        this.data = data;
        return this;
    }

    public static JsonObj success() {
        return new JsonObj().setStatus(successStatus);
    }

    public static JsonObj error(String message) {
        return new JsonObj().setStatus(errorStatus).setMsg(message);
    }

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}