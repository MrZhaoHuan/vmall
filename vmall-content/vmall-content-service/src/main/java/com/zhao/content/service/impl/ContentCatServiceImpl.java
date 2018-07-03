package com.zhao.content.service.impl;

import com.zhao.content.service.ContentCatService;
import com.zhao.mapper.ContentCategoryMapper;
import com.zhao.pojo.ContentCategory;
import com.zhao.pojo.ContentCategoryExample;
import com.zhao.util.EasyuiTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-22 15:55
 * @描述
 */
@Service
public class ContentCatServiceImpl implements ContentCatService {
    private ContentCategoryMapper categoryMapper;

    @Autowired
    public void setCategoryMapper(ContentCategoryMapper categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public List<EasyuiTree> getContentCatList(long parentId){
        //返回值
        List<EasyuiTree> trees = new ArrayList<>();
        //查询条件
        ContentCategoryExample categoryExample = new ContentCategoryExample();
        categoryExample.setOrderByClause("sort_order asc");
        categoryExample.createCriteria().andParentIdEqualTo(parentId);

        List<ContentCategory> contentCategories = categoryMapper.selectByExample(categoryExample);
        if(contentCategories!=null){
            for(ContentCategory category:contentCategories){
                EasyuiTree tree = new EasyuiTree();
                tree.setId(category.getId());
                tree.setText(category.getName());
                tree.setState(category.getIsParent()==1?"closed":"open");
                trees.add(tree);
            }
        }
        return trees;
    }

    @Override
    public long addCat(long parentId, String name) {
           ContentCategory category = new ContentCategory();
           category.setName(name);
           category.setParentId(parentId);
           int exeCount  = categoryMapper.insertAndSort(category);
           if(exeCount>0){
               //如果插入成功，把父级节点变成目录
               ContentCategory parent =  new ContentCategory();
               parent.setId(parentId);
               parent.setIsParent((byte)1);
               categoryMapper.updateByPrimaryKeySelective(parent);
           }
        return  category.getId();
    }

    @Override
    public void updateCat(long id, String name) {
        ContentCategory category =  new ContentCategory();
        category.setId(id);
        category.setName(name);
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public void deleteCat(long id) {
         categoryMapper.deleteByPrimaryKey(id);
    }

}
