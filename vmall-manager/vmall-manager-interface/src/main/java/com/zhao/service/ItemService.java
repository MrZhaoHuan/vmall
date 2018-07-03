package com.zhao.service;

import com.zhao.pojo.Item;
import com.zhao.util.DatagridObj;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-19 16:32
 * @描述
 */
public interface ItemService {

    Item getItemById(long itemId);

    DatagridObj getItemList(int page,int rows);

    void saveItem(Item item, String desc);
}
