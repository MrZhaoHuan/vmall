package com.zhao.web.client.filter;

import com.alibaba.fastjson.JSONObject;
import com.zhao.web.client.SsoUtils;
import com.zhao.web.client.ValidTicket;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-30 14:49
 * @描述
 */
public class TicketValidFilter implements Filter{
    private String interceptParam = "sso_login_ticket";
    private ValidTicket validTicket = new ValidTicket();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try{
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("sso_server.properties"));
            SsoUtils.setSso_server(properties.getProperty("sso_server"));
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            HttpServletRequest  hReq = (HttpServletRequest)request;
            HttpServletResponse hRsp = (HttpServletResponse)response;
            //todo:存在bug->虽然不影响单点登录和单点注销功能,但会清空其他子系统的session
            //创建新的session,会更新客户端的cookie:JSESSIONEDID
            HttpSession session = hReq.getSession();
            String requestUrl = hReq.getRequestURL().toString();
            String ticket = hReq.getParameter(interceptParam);
            //hReq.getLocalAddr()+":"+hReq.getLocalPort()
            String server = hReq.getScheme()+"://"+  hReq.getHeader("Host")+"/";
            if(null!=ticket && !ticket.trim().equals("")){
                 //校验ticket是否有效
                JSONObject result = validTicket.validat(ticket,server);
                if(result.getIntValue("status")!=200){
                    //重定向到sso服务器登录页面
                    String redirect = SsoUtils.getSso_server()+"user/loginPage?"
                            +"redirectUrl="+requestUrl+"&serverUrl="+server;
                    hRsp.sendRedirect(redirect);
                    return;
                }else{
                    //todo:存在bug->虽然不影响单点登录和单点注销功能,但会清空其他子系统的session
                    //把用户信息放入session中
                    session.setAttribute("user",result.getJSONObject("data"));
                    //把ticket存入cookie，用于自动登录
                    Cookie cookie = new Cookie("ticket",ticket);
                    cookie.setPath("/");
                    hRsp.addCookie(cookie);
                }
            }
        System.out.println("----------放行-----------------");
              //放行
              chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}