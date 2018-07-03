package com.zhao.util;

import java.io.Serializable;
import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-20 14:40
 * @描述  easyui的datagrid数据格式，分布式传输 实现Serializable接口
 */
public class DatagridObj<T> implements Serializable{
    private static  final long serialVersionUID  =1L;
    private long total;
    private List<T> rows;

    public DatagridObj setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public DatagridObj setRows(List<T> rows) {
        this.rows = rows;
        return this;
    }

    public List<T> getRows() {
        return rows;
    }

    public static DatagridObj buid(){
         return  new DatagridObj();
    }
}
