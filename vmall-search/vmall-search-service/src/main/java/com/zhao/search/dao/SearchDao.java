package com.zhao.search.dao;

import com.zhao.util.ItemSearch;
import com.zhao.util.SearchPageObj;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-26 18:44
 * @描述
 */
@Repository
public class SearchDao {
    private HttpSolrServer solrServer;
    @Autowired
    public void setSolrServer(HttpSolrServer solrServer) {
        this.solrServer = solrServer;
    }
    public SearchPageObj search(SolrQuery solrQuery) throws SolrServerException {
        SearchPageObj  pageObj = new SearchPageObj();
        //查询
        QueryResponse response = solrServer.query(solrQuery);
        //获取结果
        SolrDocumentList documents = response.getResults();
        Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
        long numFound = documents.getNumFound();
        //封装返回值
        pageObj.setRecourdCount(numFound);
        for(SolrDocument document:documents){
            ItemSearch item = new ItemSearch();
            item.setId((String) document.get("id"));
            item.setCat_name((String) document.get("item_category_name"));
            item.setImage((String) document.get("item_image"));
            item.setPrice((long) document.get("item_price"));
            item.setSell_point((String) document.get("item_sell_point"));
            //设置标题高亮 start
            List<String> highList = highlighting.get((String) document.get("id")).get("item_title");
            if(highList!=null&&highList.size()>0){
                item.setTitle(highList.get(0));
            }else{
                item.setTitle((String) document.get("item_title"));
            }
            //设置标题高亮 end
            pageObj.getItemList().add(item);
        }
        return  pageObj;
    }
}