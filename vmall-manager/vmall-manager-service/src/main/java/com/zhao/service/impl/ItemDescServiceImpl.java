package com.zhao.service.impl;

import com.zhao.mapper.ItemDescMapper;
import com.zhao.pojo.ItemDesc;
import com.zhao.service.ItemDescService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-28 15:20
 * @描述
 */
@Service
public class ItemDescServiceImpl implements ItemDescService {
    @Autowired
    private ItemDescMapper descMapper;

    @Override
    public ItemDesc getById(long itemId) {
        return descMapper.selectByPrimaryKey(itemId);
    }
}
