package com.zhao.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-20 14:12
 * @描述  后台页面管理
 */
@Controller
public class PageController {
    @RequestMapping("/")
    public String indexPage(){
        return  "index";
    }

    @RequestMapping("/{page}")
    public String page(@PathVariable("page") String page){
        return  page;
    }
}