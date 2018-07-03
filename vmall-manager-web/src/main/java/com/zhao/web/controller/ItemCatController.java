package com.zhao.web.controller;

import com.zhao.service.ItemCatService;
import com.zhao.util.EasyuiTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-20 18:58
 * @描述  商品管理(商品类目)
 */
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {
    private ItemCatService itemCatService;

    @Autowired
    public void setItemCatService(ItemCatService itemCatService) {
        this.itemCatService = itemCatService;
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyuiTree> list(@RequestParam(value = "id",defaultValue = "0")long parent_id){
        return  itemCatService.getItemCatByParentId(parent_id);
    }
}