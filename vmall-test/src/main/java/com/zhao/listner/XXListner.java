package com.zhao.listner;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-27 15:12
 * @描述
 */
public class XXListner implements  MyListner{
    private String name;

    public XXListner(String name){
        this.name = name;
    }
    @Override
    public void changed(Event event) {
        System.out.println("监听器:"+name+"——————>监听到商品添加："+event.getValue());
    }
}
