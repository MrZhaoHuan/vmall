package com.zhao.content.service;

import com.zhao.pojo.Content;
import com.zhao.util.DatagridObj;

import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-22 19:17
 * @描述
 */
public interface ContentService {
    DatagridObj getContentList(long categoryId, int page, int rows);

    void deleteByIds(String ids);

    void save(Content content);

    List<Content> getContentListByCid(long category_id);
}
