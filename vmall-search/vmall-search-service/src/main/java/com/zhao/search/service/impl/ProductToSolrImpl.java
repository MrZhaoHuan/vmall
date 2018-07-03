package com.zhao.search.service.impl;

import com.zhao.search.mapper.ItemSearchMapper;
import com.zhao.search.service.ProductToSolr;
import com.zhao.util.ItemSearch;
import com.zhao.util.JsonObj;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-26 13:51
 * @描述   导入商品数据到solr库
 */
@Service
public class ProductToSolrImpl implements ProductToSolr {
    private ItemSearchMapper searchMapper;
    private HttpSolrServer  solrServer;
    @Autowired
    public void setSearchMapper(ItemSearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }
    @Autowired
    public void setSolrServer(HttpSolrServer solrServer) {
        this.solrServer = solrServer;
    }

    @Override
    public JsonObj importAll(){
        JsonObj result = JsonObj.success();
        try{
            //数据库查询商品
            List<ItemSearch> itemList = searchMapper.getAll();
            //商品入solr库
            if(itemList!=null){
                for(ItemSearch item:itemList){
                    SolrInputDocument document = new SolrInputDocument();
                    document.addField("id",item.getId());
                    document.addField("item_title",item.getTitle());
                    document.addField("item_sell_point",item.getSell_point());
                    document.addField("item_price",item.getPrice());
                    document.addField("item_image",item.getImage());
                    document.addField("item_category_name", item.getCat_name());
                    solrServer.add(document);
                }
                solrServer.commit();
            }
        }catch (Exception ex){
            ex.printStackTrace();
            result = JsonObj.error("商品数据导入失败");
        }

        return result;
    }
}
