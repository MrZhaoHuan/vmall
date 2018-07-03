package com.zhao.web.controller;

import com.zhao.content.service.ContentService;
import com.zhao.pojo.Content;
import com.zhao.util.DatagridObj;
import com.zhao.util.JsonObj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-22 19:09
 * @描述  后台内容管理
 */
@RequestMapping("/content")
@Controller
public class ContentController {
    private ContentService contentService;

   // @Autowired
    public void setContentService(ContentService contentService) {
        this.contentService = contentService;
    }

    @RequestMapping("/list")
    @ResponseBody
    public DatagridObj list(@RequestParam(value = "categoryId",defaultValue = "0") long categoryId,@RequestParam("page") int page,@RequestParam("rows") int rows){
        DatagridObj datagridObj = contentService.getContentList(categoryId,page,rows);
        return datagridObj;
    }


    @RequestMapping("/delete")
    @ResponseBody
    public JsonObj deleteByIds(@RequestParam("ids") String ids){
        contentService.deleteByIds(ids);
        return JsonObj.success();
    }

    @RequestMapping("/save")
    @ResponseBody
    public JsonObj save(Content content){
        contentService.save(content);
        return JsonObj.success();
    }
}