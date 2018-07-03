package com.zhao.service;

import com.zhao.util.EasyuiTree;

import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-20 19:11
 * @描述
 */
public interface ItemCatService {
    List<EasyuiTree> getItemCatByParentId(long parent_id);
}
