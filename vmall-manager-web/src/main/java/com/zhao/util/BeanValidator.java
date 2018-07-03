package com.zhao.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.HashMap;
import java.util.Map;

/**
 * @创建人 zhaohuan
 * @邮箱 1101006260@qq.com
 * @创建时间 2018-06-12 22:52
 * @描述  JSR303校验结果
 */
public class BeanValidator{

         /*
          *@描述  获取校验结果
          *@创建时间 2018/6/12 22:55
          *@返回值 Map
          **/
        public static Map valid(BindingResult bindResult){
            Map errorMap = new HashMap<>();
            if(bindResult.hasFieldErrors()){
                 for(FieldError fieldError:bindResult.getFieldErrors()){
                     errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
                 }
            }
            return  errorMap;
        }
}