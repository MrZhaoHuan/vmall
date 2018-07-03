package com.zhao.listner;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-27 15:02
 * @描述
 */
public class Event {
    private int value;

    public Event(int value){
         this.value = value;
    }
    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
