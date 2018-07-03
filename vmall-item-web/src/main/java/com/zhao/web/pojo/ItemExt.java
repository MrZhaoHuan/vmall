package com.zhao.web.pojo;

import com.zhao.pojo.Item;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-28 15:16
 * @描述
 */
public class ItemExt extends Item {
    public ItemExt(Item item) {
        setId(item.getId());
        setTitle(item.getTitle());
        setImage(item.getImage());
        setSellPoint(item.getSellPoint());
        setPrice(item.getPrice());
    }

    public String[] getImages() {
        String image = getImage();
        if (null != image && !image.trim().equals("")) {
            return image.split(",");
        }
        return null;
    }
}
