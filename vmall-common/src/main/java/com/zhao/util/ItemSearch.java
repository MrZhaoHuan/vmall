package com.zhao.util;

import java.io.Serializable;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-26 13:38
 * @描述
 */
public class ItemSearch implements Serializable{
    private static final long serialVersionUID = 1L;
    private String id;
    private String title;
    private String sell_point;
    private long price;
    private String image;
    private String cat_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSell_point() {
        return sell_point;
    }

    public void setSell_point(String sell_point) {
        this.sell_point = sell_point;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }


    public String[] getImages(){
        String[] imgs = null;
        if(image!=null&&image.length()>0){
            imgs = image.split(",");
        }
        return imgs;
    }
}
