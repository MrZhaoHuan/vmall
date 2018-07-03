package com.zhao.web.client;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-30 13:55
 * @描述  验证ticket
 */
public class ValidTicket {

    public JSONObject validat(String ticket,String server) throws IOException {
        String sso_server = SsoUtils.getSso_server();
        // 创建Httpclient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建http POST请求
        String endChar  = String.valueOf(sso_server.charAt(sso_server.length() - 1));
        if(!endChar.equals("/")){
            sso_server = sso_server +"/";
        }
        HttpPost httpPost = new HttpPost(sso_server+"clientManage/validTicket");
        // 设置post参数
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        parameters.add(new BasicNameValuePair("ticket",ticket));
        parameters.add(new BasicNameValuePair("serverUrl",server));
        // 构造一个form表单式的实体
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        // 将请求实体设置到httpPost对象中
        httpPost.setEntity(formEntity);

        CloseableHttpResponse response = null;
        try {
            // 执行请求
            response = httpclient.execute(httpPost);
            // 判断返回状态是否为200
            if (response.getStatusLine().getStatusCode() == 200) {
                String content = EntityUtils.toString(response.getEntity(), "UTF-8");
                System.out.println("-------------------ticket校验结果---------------");
                System.out.println(content);
                return JSONObject.parseObject(content);
            }else{
                return JSONObject.parseObject("{\"status\":500,\"msg\":\"校验失败\"}");
            }
        } finally {
            if(response != null) {
                response.close();
            }
            httpclient.close();
        }
    }
}