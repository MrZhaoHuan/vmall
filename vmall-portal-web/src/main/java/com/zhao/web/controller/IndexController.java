package com.zhao.web.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-22 15:18
 * @描述  前台首页
 */
@Controller
public class IndexController {
    @Value("${shuffling_category_id}")
    private long shuffling_category_id;

  //  private ContentService contentService;

   // @Autowired
   // public void setContentService(ContentService contentService) {
   //     this.contentService = contentService;
   // }

    @RequestMapping("/index.html")
    public String indexPage(Model model){
       // List<Content> contents =  contentService.getContentListByCid(shuffling_category_id);
       // model.addAttribute("ad1List",contents);
        return  "index";
    }
}