package com.zhao.content.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zhao.content.service.ContentService;
import com.zhao.mapper.ContentMapper;
import com.zhao.pojo.Content;
import com.zhao.pojo.ContentExample;
import com.zhao.util.DatagridObj;
import com.zhao.util.jedis.JedisClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-22 19:17
 * @描述
 */
@Service
public class ContentServiceImpl implements ContentService{
    private ContentMapper contentMapper;
    private String content_category;
    private JedisClient jedisClient;
    @Value("${content_category}")
    public void setContent_category(String content_category) {
        this.content_category = content_category;
    }

    @Autowired
    public void setJedisClient(JedisClient jedisClient) {
        this.jedisClient = jedisClient;
    }

    @Autowired
    public void setContentMapper(ContentMapper contentMapper) {
        this.contentMapper = contentMapper;
    }

    @Override
    public DatagridObj getContentList(long categoryId, int page, int rows) {
        //查询条件
        PageHelper.startPage(page, rows);
        ContentExample contentExample = new ContentExample();
        contentExample.createCriteria().andCategoryIdEqualTo(categoryId);
        //查询
        List<Content> contents = contentMapper.selectByExampleWithBLOBs(contentExample);
        if(contents==null){
            contents = new ArrayList<>();
        }
        PageInfo<Content> contentPageInfo = new PageInfo(contents);
        return DatagridObj.buid().setTotal(contentPageInfo.getTotal()).setRows(contents);
    }

    @Override
    public void deleteByIds(String ids) {
        //条件
        String[] idArr = ids.split(",");
        List idList = new ArrayList<>();
        for(String id:idArr){
            idList.add(Long.parseLong(id));
        }
        ContentExample contentExample = new ContentExample();
        ContentExample.Criteria criteria = contentExample.createCriteria();
        criteria.andIdIn(idList);
        //删除
        contentMapper.deleteByExample(contentExample);
    }

    @Override
    public void save(Content content) {
        contentMapper.insertSelective(content);
        //删除redis中的数据
        jedisClient.hdel(content_category,content.getCategoryId()+"");
    }

    @Override
    public List<Content> getContentListByCid(long category_id) {
        try{
            //先从redis缓存中查询
            String contents = jedisClient.hget(content_category, category_id + "");
            if(contents!=null){
                //返回缓存中的数据
               return JSONObject.parseArray(contents,Content.class);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        //查询条件
        ContentExample contentExample = new ContentExample();
        contentExample.createCriteria().andCategoryIdEqualTo(category_id);
        //查询
        List<Content> contents = contentMapper.selectByExample(contentExample);
        if(contents==null){
            contents = new ArrayList<>();
        }
        try{
            //添加数据到redis缓存
            jedisClient.hset(content_category,category_id+"",JSONObject.toJSONString(contents));
        }catch (Exception ex){
            ex.printStackTrace();
        }
       return  contents;
    }
}