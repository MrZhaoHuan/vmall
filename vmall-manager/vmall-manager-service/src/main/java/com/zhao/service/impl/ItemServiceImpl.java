package com.zhao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.mapper.ItemDescMapper;
import com.zhao.mapper.ItemMapper;
import com.zhao.pojo.Item;
import com.zhao.pojo.ItemDesc;
import com.zhao.pojo.ItemExample;
import com.zhao.service.ItemService;
import com.zhao.util.DatagridObj;
import com.zhao.util.IDGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.*;
import java.util.Date;
import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-19 16:33
 * @描述
 */
@Service
public class ItemServiceImpl implements ItemService{
    private ItemMapper itemMapper;
    private ItemDescMapper itemDescMapper;
    private JmsTemplate jmsTemplate;
    private Destination destination;

    @Resource(name = "item_topic")
    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Autowired
    public void setJmsTemplate(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    @Autowired
    public void setItemDescMapper(ItemDescMapper itemDescMapper) {
        this.itemDescMapper = itemDescMapper;
    }

    @Autowired
    public void setItemMapper(ItemMapper itemMapper) {
        this.itemMapper = itemMapper;
    }

    @Override
    public Item getItemById(long itemId) {
        return itemMapper.selectByPrimaryKey(itemId);
    }

    @Override
    public DatagridObj getItemList(int page, int rows) {
        PageHelper.startPage(page, rows);
        List<Item> items = itemMapper.selectByExample(new ItemExample());
        PageInfo<Item> itemPageInfo = new PageInfo(items);
        return DatagridObj.buid().setTotal(itemPageInfo.getTotal()).setRows(items);
    }

    @Override
    public void saveItem(Item item, String desc) {
            Date date = new Date();
            long id = IDGenerator.generateId();
            //添加商品
            item.setCreated(date);
            item.setUpdated(date);
            item.setStatus((byte) 1);  //商品状态，1-正常，2-下架，3-删除
            item.setId(id);
            itemMapper.insertSelective(item);
            //添加商品描述
            ItemDesc itemDesc=  new ItemDesc();
            itemDesc.setUpdated(date);
            itemDesc.setCreated(date);
            itemDesc.setItemDesc(desc);
            itemDesc.setItemId(id);
            itemDescMapper.insertSelective(itemDesc);

            try{
                //发送消息到消息队列，更新索引库
                jmsTemplate.send(destination, new MessageCreator() {
                    @Override
                    public Message createMessage(Session session) throws JMSException {
                        TextMessage message = session.createTextMessage(Long.toString(item.getId()));
                        return message;
                    }
                });
            }catch (Exception ex){
                ex.printStackTrace();
            }
    }

}