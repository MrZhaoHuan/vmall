package com.zhao.web.controller;

import com.zhao.search.service.ProductToSolr;
import com.zhao.util.JsonObj;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-26 15:21
 * @描述   导入商品到索引库
 */
@Controller
public class SolrImportController {
    private ProductToSolr toSolr;

    //@Autowired
    public void setToSolr(ProductToSolr toSolr) {
        this.toSolr = toSolr;
    }

    @RequestMapping("/index/item/import")
    @ResponseBody
    public JsonObj importToSolr(){
        JsonObj  obj  =toSolr.importAll();
          return  obj;
    }
}