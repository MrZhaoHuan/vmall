package com.zhao.search.service.impl;

import com.zhao.search.dao.SearchDao;
import com.zhao.search.service.SearchService;
import com.zhao.util.SearchPageObj;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-26 18:29
 * @描述
 */
@Service
public class SearchServiceImpl implements SearchService{
    @Value("${search_page_rows}")
    private int search_page_rows;
    @Value("${search_defaultField}")
    private String defaultField; //默认搜索的key

    private SearchDao searchDao;
    @Autowired
    public void setSearchDao(SearchDao searchDao) {
        this.searchDao = searchDao;
    }

    @Override
    public SearchPageObj search(String keyword, int page){
        try {
            //封装查询参数
            SolrQuery solrQ = new SolrQuery();
            //设置默认查询的field
            solrQ.set("df",defaultField);
            //设置查询值
            solrQ.setQuery(keyword);
            //设置分页
            solrQ.setStart((page-1)*search_page_rows);
            solrQ.setRows(search_page_rows);
            //设置高亮
            solrQ.setHighlight(true);
            solrQ.setHighlightSimplePre("<span style='color:red'>");
            solrQ.setHighlightSimplePost("</span>");
            solrQ.addHighlightField(defaultField);
            //执行查询
            SearchPageObj pageObj = searchDao.search(solrQ);
            pageObj.setTotalPages((long)Math.ceil((double)pageObj.getRecourdCount()/search_page_rows));
            return  pageObj;
        } catch (SolrServerException e) {
            e.printStackTrace();
        }
        return new SearchPageObj();
    }
}