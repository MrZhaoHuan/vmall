package com.zhao.web.exception;

import com.zhao.util.JsonObj;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-12 14:50
 * @描述 全局异常处理器
 */
public class SpringExceptionResolver implements HandlerExceptionResolver {
    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        ModelAndView mv = new ModelAndView("jsonView",null);
        String defaultErro = "system error";
        if(ex instanceof  CustomException){
            mv.addObject(JsonObj.error(ex.getMessage()));
        }else{
            mv.addObject(JsonObj.error(defaultErro));
        }
        return mv;
    }
}