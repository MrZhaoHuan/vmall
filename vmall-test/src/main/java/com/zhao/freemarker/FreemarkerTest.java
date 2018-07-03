package com.zhao.freemarker;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import java.io.*;
import java.util.*;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-28 20:46
 * @描述   freemarker测试
 */

public class FreemarkerTest {
    //@Test
    public void t1() throws IOException, TemplateException {
        String classPath = getClass().getResource("/").getPath();
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        //指定模板目录
        configuration.setDirectoryForTemplateLoading(new File(classPath+"ftl"));
        //获取模板对象
        Template template = configuration.getTemplate("hello.ftl");
        //模板数据
        Map data = new HashMap<>();
        data.put("name","zhaohuan");
        //对模版进行处理，写入到输出流
        //template.process(data,new OutputStreamWriter(System.out));
        //FileWriter fileWriter  = new FileWriter(new File(classPath+"html/hello.html"));
        //template.process(data,fileWriter);

        FileWriter fileWriter  = new FileWriter(new File("E:\\Program Files\\apache-tomcat-7.0.78-copy\\webapps\\ROOT\\hello.html"));
        template.process(data,fileWriter);
        fileWriter.close();
    }

    @Test
    public void t2() throws IOException, TemplateException {
        String classPath = getClass().getResource("/").getPath();
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
        //指定模板目录
        configuration.setDirectoryForTemplateLoading(new File(classPath + "ftl"));
        //获取模板对象
        Template template = configuration.getTemplate("hello.ftl");
        Map data  = t3();
        //对模版进行处理，写入到输出流
        FileWriter fileWriter  = new FileWriter(new File("E:\\Program Files\\apache-tomcat-7.0.78-copy\\webapps\\ROOT\\hello.html"));
        template.process(data,fileWriter);
        fileWriter.close();
    }

    private Map t3() {
        //模板数据
        Map data = new HashMap<>();
        List<User> userList = new ArrayList<>();
        userList.add(new User(3,"zhao",100,new Date()));
        userList.add(new User(6,"xiaoli",200,new Date()));
        userList.add(new User(9,null,300,new Date()));
        data.put("userList",userList);
        return  data;
    }
}