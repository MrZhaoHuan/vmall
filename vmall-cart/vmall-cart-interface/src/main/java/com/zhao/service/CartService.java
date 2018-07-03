package com.zhao.service;

import com.zhao.pojo.Item;

import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-07-01 18:53
 * @描述
 */
public interface CartService {
    boolean mergeCart(List<Item> cartItem);

    List<Item> getCart();

    boolean deleteCartById(long itemId);

    boolean updateCartById(long itemId, int num);
}
