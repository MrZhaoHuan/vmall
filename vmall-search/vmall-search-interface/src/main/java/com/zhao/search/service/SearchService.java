package com.zhao.search.service;

import com.zhao.util.SearchPageObj;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-26 18:30
 * @描述
 */
public interface SearchService{
    SearchPageObj search(String keyword,int page);
}
