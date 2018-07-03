package com.zhao.web.controller;

import com.zhao.search.service.SearchService;
import com.zhao.util.SearchPageObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.UnsupportedEncodingException;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-26 18:12
 * @描述  前台商品搜索
 */
@Controller
public class SearchController {
    private SearchService searchService;

    @Autowired
    public void setSearchService(SearchService searchService) {
        this.searchService = searchService;
    }

    @RequestMapping(value = "/search.html",method = RequestMethod.GET)
    public String search(@RequestParam(value = "keyword",defaultValue = "")String keyword,@RequestParam(value = "page",defaultValue = "1")int page,Model model){
        //参数重置
        if(page<1){page=1;}
        try {
            keyword = new String(keyword.getBytes("iso8859-1"),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        SearchPageObj pageObj = searchService.search(keyword, page);
        model.addAttribute("recourdCount",pageObj.getRecourdCount());
        model.addAttribute("itemList",pageObj.getItemList());
        model.addAttribute("totalPages",pageObj.getTotalPages());
        model.addAttribute("query",keyword);
        model.addAttribute("page",page);
        return "search";
    }
}