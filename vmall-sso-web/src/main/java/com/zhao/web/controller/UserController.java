package com.zhao.web.controller;

import com.zhao.pojo.User;
import com.zhao.sso.service.UserService;
import com.zhao.util.JsonObj;
import com.zhao.web.container.LoginSubServer;
import com.zhao.web.utils.HttpClientUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-29 20:06
 * @描述
 */
@RequestMapping("/user")
@Controller
public class UserController {
    @Autowired
    private UserService userService;
    private String sso_ticket = "sso_login_ticket";
    //用户注册
    @RequestMapping("/register")
    @ResponseBody
    public JsonObj register(@Valid User user,BindingResult bindingResult){
        //校验参数
        //插入数据
        boolean isOk = userService.saveUser(user);
        if(!isOk){
            return JsonObj.error("注册失败");
        }
        return  JsonObj.success();
    }

    //注册页面
    @RequestMapping(method = RequestMethod.GET,value = "/registerPage")
    public String registePage(){
        return  "register";
    }

    //注册：用户名和手机号校验
    @RequestMapping("/check/{param}/{type}")
    @ResponseBody
    public JsonObj check(@PathVariable("param") String param,@PathVariable("type") int type){
        boolean isOk = userService.validUserAndPhone(param,type);
        if(!isOk){
             if(type==1){
                 return JsonObj.error("用户名已注册");
             }else{
                 return JsonObj.error("手机号已注册");
             }
        }
        return  JsonObj.success();
    }


    //登录页面
    @RequestMapping("/loginPage")
    public String loginPage(Model model,@RequestParam(value = "serverUrl") String serverUrl,@RequestParam(value = "redirectUrl",defaultValue = "") String redirectUrl,HttpServletRequest request){
        model.addAttribute("redirectUrl",getRedirectUrl(request,redirectUrl));
        model.addAttribute("serverUrl",serverUrl);
        return  "login";
    }

    private  String getRedirectUrl(HttpServletRequest request,String redirectUrl){
        if(redirectUrl.equals("")){
            String scheme = request.getScheme();
            String remoteHost = request.getRemoteHost();
            int remotePort = request.getRemotePort();
            redirectUrl = scheme+"://"+remoteHost+":"+remotePort;
        }else{
            String endChar  = String.valueOf(redirectUrl.charAt(redirectUrl.length()-1));
            if(endChar.equals("/")){
                redirectUrl = redirectUrl.substring(0,redirectUrl.length()-1);
            }
        }

        return  redirectUrl;
    }

    //登录(手机号或用户名登录)
    @RequestMapping("/login")
    @ResponseBody
    public JsonObj login(@RequestParam(value = "serverUrl") String serverUrl,@RequestParam("username")String user,@RequestParam("password")String password,HttpServletRequest request){
        JsonObj loginResult = userService.loginUser(user,password);
        if(loginResult.getStatus()==500){
            return loginResult;
        }
        ((Map)loginResult.getData()).put("sso_ticket_key",sso_ticket);
        ((Map)loginResult.getData()).put("sso_ticket_val",((Map)loginResult.getData()).get("ticket"));
        //登录成功后，状态变为1-有效，用于注销
        //放redis或者sso服务器内存
        LoginSubServer.addServer(serverUrl);
        return  loginResult;
    }

    //单点注销
    @RequestMapping("/logout")
    @ResponseBody
    public JsonObj logout(){
       //清空sso-redis
        userService.invalidUser();
        //注销子系统
        Set<String> loginList = LoginSubServer.getLoginList();
        for(String server:loginList){
            try {
                HttpClientUtils.get(server+"logout");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //清空子系统列表
        LoginSubServer.removeLoginList();
        return JsonObj.success();
    }
}