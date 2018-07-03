package com.zhao.web.controller;

import com.zhao.content.service.ContentCatService;
import com.zhao.util.EasyuiTree;
import com.zhao.util.JsonObj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-22 16:08
 * @描述   后台内容分类管理
 */
@RequestMapping("/content/category")
@Controller
public class ContentCatController {
    private ContentCatService catService;

   // @Autowired
    public void setCatService(ContentCatService catService) {
        this.catService = catService;
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<EasyuiTree> list(@RequestParam(value = "id",defaultValue = "0") long parentId){
            return  catService.getContentCatList(parentId);
    }

    @RequestMapping("/create")
    @ResponseBody
    public JsonObj add(long parentId,String name){
        long insertReturnId = catService.addCat(parentId,name);
        return  JsonObj.success().setData(Collections.singletonMap("id", insertReturnId));
    }

    @RequestMapping("/update")
    @ResponseBody
    public JsonObj updateCat(long id,String name){
        catService.updateCat(id,name);
        return  JsonObj.success();
    }


    @RequestMapping("/delete")
    @ResponseBody
    public JsonObj deleteCat(long id){
        catService.deleteCat(id);
        return  JsonObj.success();
    }


}
