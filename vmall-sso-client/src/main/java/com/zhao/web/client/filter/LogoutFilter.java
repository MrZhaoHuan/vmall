package com.zhao.web.client.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-30 17:56
 * @描述  注销
 */
public class LogoutFilter implements Filter{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    //拦截sso-服务器的注销请求
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest)request).getSession();
        System.out.println("注销请求");
        session.setAttribute("user",null);
    }

    @Override
    public void destroy() {

    }
}
