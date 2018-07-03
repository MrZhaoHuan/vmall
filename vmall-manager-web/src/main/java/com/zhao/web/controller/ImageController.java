package com.zhao.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.zhao.util.FastDFSClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-21 21:30
 * @描述  图片上传
 */
@RequestMapping("/pic")
@Controller
public class ImageController {

    @Value("${img_server_url}")
    private String img_server_url;

    @RequestMapping(value = "/upload",produces = {MediaType.TEXT_PLAIN_VALUE+";charset=utf-8"} )
    @ResponseBody
    public String upload(@RequestParam("uploadFile") MultipartFile uploadFile){
        try {
            Map result = new HashMap<>();
            String fileName = uploadFile.getOriginalFilename();
            FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastdfs-clinet.config");
            String fileUrl = fastDFSClient.uploadFile(uploadFile.getBytes(), fileName.substring(fileName.lastIndexOf(".") + 1));
            result.put("error",0);
            result.put("url",img_server_url+fileUrl);
            System.out.println(fileUrl);
            return JSONObject.toJSONString(result);
        } catch (Exception e){
            e.printStackTrace();
            return JSONObject.toJSONString("{\"error\":1,\"message\":\"文件上传失败\"}");
        }
    }
}