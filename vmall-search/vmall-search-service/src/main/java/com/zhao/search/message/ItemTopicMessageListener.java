package com.zhao.search.message;

import com.zhao.search.mapper.ItemSearchMapper;
import com.zhao.util.ItemSearch;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.io.IOException;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-28 13:59
 * @描述
 */
public class ItemTopicMessageListener implements MessageListener{
    private ItemSearchMapper searchMapper;
    private HttpSolrServer solrServer;
    @Autowired
    public void setSolrServer(HttpSolrServer solrServer) {
        this.solrServer = solrServer;
    }
    @Autowired
    public void setSearchMapper(ItemSearchMapper searchMapper) {
        this.searchMapper = searchMapper;
    }

    @Override
    public void onMessage(Message message) {
        try {
            //商品id
            Long itemId = Long.parseLong(((TextMessage)message).getText());
            Thread.sleep(1000);
            //查询数据库
            ItemSearch item = searchMapper.getById(itemId);
            if(null!=item){
                //更新索引库
                SolrInputDocument document = new SolrInputDocument();
                document.addField("id",item.getId());
                document.addField("item_title",item.getTitle());
                document.addField("item_sell_point",item.getSell_point());
                document.addField("item_price",item.getPrice());
                document.addField("item_image",item.getImage());
                document.addField("item_category_name", item.getCat_name());
                solrServer.add(document);
                solrServer.commit();
            }
        } catch (JMSException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
    }
}