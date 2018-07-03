package com.zhao.listner;

import java.util.HashSet;
import java.util.Set;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-27 15:00
 * @描述
 */
public class ProductDao{
     private Set<MyListner> listners = new HashSet();
    private int pid;

    public void addMyListner(MyListner myListner) {
         listners.add(myListner);
    }

    public boolean add(int  pId){
         this.pid = pId;
         System.out.println("添加了商品,ID是:"+pId);
         trigger(listners); //异步通知
         return true;
     }

    private void trigger(Set<MyListner> listners) {
          //广播
          for(MyListner listner:listners){
              new Thread(){
                  @Override
                  public void run() {
                      try {
                          Thread.sleep(3000);
                      } catch (InterruptedException e) {
                          e.printStackTrace();
                      }
                      listner.changed(new Event(pid));
                  }
              }.start();
          }
    }
}
