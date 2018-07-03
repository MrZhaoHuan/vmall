package com.zhao.web.controller;

import com.zhao.service.ItemDescService;
import com.zhao.service.ItemService;
import com.zhao.web.pojo.ItemExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-28 15:05
 * @描述
 */
@Controller
public class IndexController{
    @Autowired
    private ItemDescService descService;
    @Autowired
    private ItemService itemService;


    @RequestMapping("/item/{itemId}")
    public String  getItemById(@PathVariable("itemId") long itemId,Model model){
        model.addAttribute("item",new ItemExt(itemService.getItemById(itemId)));
        model.addAttribute("itemDesc",descService.getById(itemId));
        System.out.println(model);
        return  "item";
    }
}
