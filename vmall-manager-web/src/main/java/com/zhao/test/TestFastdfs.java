package com.zhao.test;

import com.zhao.util.FastDFSClient;

import java.io.FileInputStream;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-21 15:43
 * @描述
 */
public class TestFastdfs {
    public static void main(String[] args) throws Exception {
        FastDFSClient fastDFSClient = new FastDFSClient("classpath:fastdfs-clinet.config");
        FileInputStream fileIn = new FileInputStream("C:\\Users\\MrZhao\\Desktop\\java大师路线图.jpg");
        byte[] data = new byte[1024*516];
        fileIn.read(data);
        String fileUrl = fastDFSClient.uploadFile(data,"jpg");
        System.out.println(fileUrl);
    }
}
