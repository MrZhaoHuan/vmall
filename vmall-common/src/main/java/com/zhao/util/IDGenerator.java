package com.zhao.util;

import java.util.Date;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-22 10:51
 * @描述      数据库主键id生成
 */
public class IDGenerator{
    private static  int cycle = 10;
    public static long generateId(){
        long milli = new Date().getTime();
        synchronized (IDGenerator.class){
            milli = Long.parseLong(milli+""+cycle);
            if(++cycle>99){
                cycle = 10;
            }
        }
        return  milli;
    }
}