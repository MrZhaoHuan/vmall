package com.zhao.search;

import com.zhao.search.service.ProductToSolr;
import com.zhao.util.JsonObj;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-26 14:51
 * @描述  测试商品导入solr库
 */
public class TestImport {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ProductToSolr toSolr = context.getBean(ProductToSolr.class);
        JsonObj jsonObj = toSolr.importAll();
        System.out.println(jsonObj);
    }
}
