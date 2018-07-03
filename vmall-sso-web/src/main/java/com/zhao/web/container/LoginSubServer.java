package com.zhao.web.container;

import com.alibaba.dubbo.common.utils.ConcurrentHashSet;

import java.util.Set;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-30 18:34
 * @描述     记录子系统列表，用于注销
 */
public class LoginSubServer {
    private static Set loginList = new ConcurrentHashSet<>();

    public static boolean addServer(String server){
         return  loginList.add(server);
    }

    public static Set getLoginList() {
        return loginList;
    }

    public static void removeLoginList() {
        loginList =  new ConcurrentHashSet<>();
    }
}