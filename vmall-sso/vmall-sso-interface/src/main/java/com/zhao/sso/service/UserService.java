package com.zhao.sso.service;

import com.zhao.pojo.User;
import com.zhao.util.JsonObj;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-29 20:42
 * @描述
 */
public interface UserService {

    boolean validUserAndPhone(String param, int type);

    boolean saveUser(User user);

    JsonObj loginUser(String user, String password);

    JsonObj validTicket(String ticket);

    void invalidUser();
}
