package com.zhao.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-26 18:31
 * @描述  前台搜索返回
 */
public class SearchPageObj implements Serializable{
    private static  final long serialVersionUID  =1L;
    private long totalPages;  //总页数
    private long recourdCount; //总条数
    private List<ItemSearch> itemList= new ArrayList<>(); //每页的数据

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getRecourdCount() {
        return recourdCount;
    }

    public void setRecourdCount(long recourdCount) {
        this.recourdCount = recourdCount;
    }

    public List<ItemSearch> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemSearch> itemList) {
        this.itemList = itemList;
    }
}
