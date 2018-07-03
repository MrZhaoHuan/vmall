package com.zhao.search.mapper;

import com.zhao.util.ItemSearch;

import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-26 13:37
 * @描述
 */
public interface ItemSearchMapper {
    List<ItemSearch> getAll();
    ItemSearch getById(long id);
}