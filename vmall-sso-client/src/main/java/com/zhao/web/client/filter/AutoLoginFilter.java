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
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-30 17:56
 * @描述  登录校验
 */
public class AutoLoginFilter implements Filter{
    private List<String> resourc_url_list;
    private ValidTicket validTicket = new ValidTicket();
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try{
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("intercept_resourc_url.properties"));
            resourc_url_list = Arrays.asList(properties.getProperty("resourc_url").split(";"));
        }catch (IOException ex){
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest hReq = (HttpServletRequest)request;
        HttpServletResponse hRsp = (HttpServletResponse)response;
        HttpSession session = hReq.getSession();
        JSONObject user = (JSONObject)session.getAttribute("user");
        String servletPath = hReq.getServletPath();
        //hReq.getLocalAddr()+":"+hReq.getLocalPort()

        String server = hReq.getScheme()+"://"+ hReq.getHeader("Host") + "/";
        //如果没登录
        System.out.println(user);
        if(null==user){
            //如果请求的资源需要授权，则重定向sso登录
            if(resourc_url_list.indexOf(servletPath)!=-1){
                String sso_server  = SsoUtils.getSso_server();
                String endChar  = String.valueOf(sso_server.charAt(sso_server.length() - 1));
                if(!endChar.equals("/")){
                    sso_server = sso_server +"/";
                }
                //校验cookie，自动登录
                Cookie[] cookies = hReq.getCookies();
                String ticket = "";
                if(null!=cookies){
                    for(Cookie cookie:cookies){
                        if(cookie.getName().equals("ticket")){
                            ticket = cookie.getValue();
                            break;
                        }
                    }
                }

                if(!"".equals(ticket)){
                    //校验ticket是否有效
                    JSONObject result = validTicket.validat(ticket,server);
                    if(result.getIntValue("status")==200){
                        //把用户信息放入session中
                        session.setAttribute("user", result.getJSONObject("data"));
                        chain.doFilter(request,response);
                        return;
                    }
                }
                //重定向到sso去登录
                hRsp.sendRedirect(sso_server+"user/loginPage?"
                        +"redirectUrl="+hReq.getRequestURL()+"&serverUrl="+server);
                return;
            }
        }
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
