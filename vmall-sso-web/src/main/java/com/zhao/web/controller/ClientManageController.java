package com.zhao.web.controller;

import com.zhao.sso.service.UserService;
import com.zhao.util.JsonObj;
import com.zhao.web.container.LoginSubServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-30 13:57
 * @描述  接受客户端ticket校验
 */
@Controller
@RequestMapping("/clientManage")
public class ClientManageController{
    @Autowired
    private UserService userService;

        @RequestMapping("/validTicket")
        @ResponseBody
        public JsonObj validTicket(HttpServletResponse response,@RequestParam("ticket") String ticket,@RequestParam("serverUrl") String serverUrl){
            //redis中校验ticket有效性
            //放redis或者sso服务器内存
            response.setHeader("Access-Control-Allow-Origin", "*");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
            response.setHeader("Access-Control-Max-Age", "3600");
            response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
            LoginSubServer.addServer(serverUrl);
        return  userService.validTicket(ticket);
    }
}