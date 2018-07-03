package com.zhao.sso.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhao.mapper.UserMapper;
import com.zhao.pojo.User;
import com.zhao.pojo.UserExample;
import com.zhao.sso.service.UserService;
import com.zhao.util.JsonObj;
import com.zhao.util.jedis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-29 20:41
 * @描述
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;
    private JedisClient jedisClient;
    @Value("${SESSION_TICKET}")
    public String SESSION_TICKET;
    @Autowired
    public void setJedisClient(JedisClient jedisClient) {
        this.jedisClient = jedisClient;
    }

    @Override
    public boolean validUserAndPhone(String param, int type) {
         UserExample userExample = new UserExample();
        if(type==1){
            userExample.createCriteria().andUsernameEqualTo(param);
        }else{
            userExample.createCriteria().andPhoneEqualTo(param);
        }
        //根据用户名或手机号查询
        List<User> users = userMapper.selectByExample(userExample);
        if(null!=users && users.size()>0 ){
            return false;
        }
        return true;
    }

    @Override
    public boolean saveUser(User user) {
        //加密——>用户的密码
        String md5_pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5_pwd);
        int count = userMapper.insertSelective(user);
        return count>0?true:false;
    }

    @Override
    public JsonObj loginUser(String user, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andPasswordEqualTo(DigestUtils.md5DigestAsHex(password.getBytes()))
                .andUsernameEqualTo(user);
        List<User> users = userMapper.selectByExample(userExample);
        if(null==users || users.size()==0){
            //登录失败
            return JsonObj.error("用户名或密码错误");
        }else{
            //登录成功
            //存入redis中——>  uuid:用户信息
            String uuid = UUID.randomUUID().toString();
            jedisClient.set(SESSION_TICKET,uuid+":"+JSONObject.toJSONString(users.get(0)));
            jedisClient.expire(SESSION_TICKET,60); //todo:60秒过期,方便测试
            Map data = new HashMap<>();
            data.put("ticket",uuid);
            return JsonObj.success().setData(data);
        }
    }

    @Override
    public JsonObj validTicket(String ticket) {
        String ticketInfo = jedisClient.get(SESSION_TICKET);
        if(null!=ticketInfo && !ticketInfo.trim().equals("")){
            int ticketIndex = ticketInfo.indexOf(":");
            String userInfo = ticketInfo.substring(ticketIndex+1,ticketInfo.length());
            System.out.println(userInfo);
            if(ticket.equals(ticketInfo.substring(0,ticketIndex))){
                //ticket有效，重置过期时间
                jedisClient.expire(SESSION_TICKET, 60); //todo:60秒过期,方便测试
                return  JsonObj.success().setData(JSONObject.parseObject(userInfo));
            }
        }
        return JsonObj.error("用户登录已过期");
    }

    @Override
    public void invalidUser() {
        jedisClient.del(SESSION_TICKET);
    }
}