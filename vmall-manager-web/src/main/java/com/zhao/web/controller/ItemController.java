package com.zhao.web.controller;

import com.zhao.pojo.Item;
import com.zhao.service.ItemService;
import com.zhao.util.BeanValidator;
import com.zhao.util.DatagridObj;
import com.zhao.util.JsonObj;
import com.zhao.web.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.Map;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-19 16:26
 * @描述  商品管理(商品)
 */
@Controller
@RequestMapping("/item")
public class ItemController{

    private ItemService itemService;

    @Autowired
    public void setItemService(ItemService itemService) {
        this.itemService = itemService;
    }

    @RequestMapping("/{itemId}")
    @ResponseBody
    public Item getItem(@PathVariable("itemId") long itemId){
        return  itemService.getItemById(itemId);
    }

    @RequestMapping("/list")
    @ResponseBody
    public DatagridObj getItemList(@RequestParam("page") int page,@RequestParam("rows") int rows){
           return  itemService.getItemList(page, rows);
    }


    @RequestMapping("/save")
    @ResponseBody
    public JsonObj save(@Valid Item item,BindingResult bindingResult,@RequestParam("desc") String desc){
        Map validResult = BeanValidator.valid(bindingResult);
        if(!validResult.isEmpty()){
            throw new CustomException(validResult.toString());
        }

        itemService.saveItem(item,desc);
        return  JsonObj.success();
    }
}