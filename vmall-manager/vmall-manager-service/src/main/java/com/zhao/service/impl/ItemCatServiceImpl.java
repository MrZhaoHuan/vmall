package com.zhao.service.impl;

import com.zhao.mapper.ItemCatMapper;
import com.zhao.pojo.ItemCat;
import com.zhao.pojo.ItemCatExample;
import com.zhao.service.ItemCatService;
import com.zhao.util.EasyuiTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-20 19:12
 * @描述
 */
@Service
public class ItemCatServiceImpl implements ItemCatService{
    private ItemCatMapper itemCatMapper;

    @Autowired
    public void setItemCatMapper(ItemCatMapper itemCatMapper) {
        this.itemCatMapper = itemCatMapper;
    }

    @Override
    public List<EasyuiTree> getItemCatByParentId(long parent_id) {
        List<EasyuiTree> easyuiTrees = new ArrayList<>();

        //查询条件
        ItemCatExample itemCatExample = new ItemCatExample();
        itemCatExample.createCriteria()
                         .andParentIdEqualTo(parent_id);
        itemCatExample.setOrderByClause("sort_order asc");

        List<ItemCat> itemCats = itemCatMapper.selectByExample(itemCatExample);
        if(itemCats!=null){
            for(ItemCat itemCat:itemCats){
                 EasyuiTree easyuiTree = EasyuiTree.buid()
                         .setId(itemCat.getId())
                         .setText(itemCat.getName())
                         .setState(itemCat.getIsParent() == 1 ? "closed":"open");
                 easyuiTrees.add(easyuiTree);
            }
        }
        return easyuiTrees;
    }
}
