package com.zhao.web.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.zhao.web.client.ValidTicket;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-07-01 18:45
 * @描述
 */
public class LoginInfoInterceptor extends HandlerInterceptorAdapter{
    private ValidTicket validTicket = new ValidTicket();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String ticket = "";
        String server = request.getScheme()+"://"+  request.getHeader("Host")+"/";
        Cookie[] cookies =  request.getCookies();
        if(null!=cookies){
            for(Cookie cookie:cookies){
                if(cookie.getName().equals("ticket")){
                    ticket = cookie.getValue();
                    break;
                }
            }
        }
        if(!"".equals(ticket)){
            JSONObject result = validTicket.validat(ticket,server);
            HttpSession session = request.getSession();
            if(result.getIntValue("status")==200){
                //把用户信息放入session中
                session.setAttribute("user", result.getJSONObject("data"));
            }
        }
        return true;
    }
}
