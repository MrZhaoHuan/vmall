package com.zhao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zhao.pojo.Item;
import com.zhao.service.CartService;
import com.zhao.util.jedis.JedisClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-07-01 18:54
 * @描述
 */
@Service
public class CartServiceImpl implements CartService{
    private JedisClient jedisClient;
    @Value("${SESSION_CART}")
    public String SESSION_CART;
    @Autowired
    public void setJedisClient(JedisClient jedisClient) {
        this.jedisClient = jedisClient;
    }

    @Override
    public boolean mergeCart(List<Item> cartItem){
        for(Item item:cartItem){
            String StringItem = jedisClient.hget(SESSION_CART, item.getId().toString());
            if(StringUtils.isEmpty(StringItem)){
                jedisClient.hset(SESSION_CART,item.getId().toString(), JSONObject.toJSONString(item));
            }else{
                Item redisItem = JSONObject.parseObject(StringItem, Item.class);
                redisItem.setNum(redisItem.getNum()+item.getNum());
                jedisClient.hset(SESSION_CART, redisItem.getId().toString(), JSONObject.toJSONString(redisItem));
            }
        }
        return true;
    }

    @Override
    public List<Item> getCart() {
        List<Item> itemList = new ArrayList<>();
        List<String> stringList = jedisClient.hvals(SESSION_CART);
        if(stringList!=null){
            for(String strItem:stringList){
                itemList.add(JSONObject.parseObject(strItem,Item.class));
            }
        }
        return itemList;
    }

    @Override
    public boolean deleteCartById(long itemId) {
        jedisClient.hdel(SESSION_CART,String.valueOf(itemId));
        return true;
    }

    @Override
    public boolean updateCartById(long itemId, int num) {
        String itemStr = jedisClient.hget(SESSION_CART, String.valueOf(itemId));
        if(StringUtils.isNotEmpty(itemStr)){
            Item itemObj = JSONObject.parseObject(itemStr, Item.class);
            itemObj.setNum(num);
            //重新放到redis中
            jedisClient.hset(SESSION_CART, String.valueOf(itemId), JSONObject.toJSONString(itemObj));
        }
        return true;
    }
}