package com.zhao.web.controller;

import com.zhao.pojo.Item;
import com.zhao.service.CartService;
import com.zhao.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-07-02 13:23
 * @描述
 */
@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private CartService cartService;

    @RequestMapping("/order-cart")
    public String index(Model model){
        List<Item> itemList = cartService.getCart();
        model.addAttribute("cartList",itemList);
        return "order-cart";
    }


    @RequestMapping("/create")
    public String create(){
       return null;
    }
}