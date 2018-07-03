package com.zhao.content.service;

import com.zhao.util.EasyuiTree;

import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-22 15:54
 * @描述
 */
public interface ContentCatService {
     List<EasyuiTree> getContentCatList(long parentId);

     long addCat(long parentId, String name);

     void updateCat(long id,String name);

     void deleteCat(long id);
}
