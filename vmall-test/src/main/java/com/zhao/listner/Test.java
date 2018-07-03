package com.zhao.listner;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-27 15:13
 * @描述
 */
public class Test {
    public static void main(String[] args) {
         ProductDao dao = new ProductDao();
         dao.addMyListner(new XXListner("项目经理"));
         dao.addMyListner(new XXListner("客户"));
         dao.addMyListner(new XXListner("屌丝程序员"));
         boolean isSucc = dao.add(520);
         System.out.println(isSucc);
    }
}