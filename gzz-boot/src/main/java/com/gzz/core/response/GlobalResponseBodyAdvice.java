package com.gzz.core.response;

import com.alibaba.fastjson.JSON;
import com.gzz.core.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

/**
 * 响应请求body全局统一处理
 * 响应请求，加密操作
 *
 */
@Component
@ControllerAdvice(basePackages = "com.gzz.wget.wgeit.api")
@Slf4j
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        // 通过serverHttpRequest的实现类ServletServerHttpRequest 获取HttpServletRequest
        ServletServerHttpRequest sshr = (ServletServerHttpRequest) request;
        // 获取 HttpServletRequest 对象
        HttpServletRequest req = sshr.getServletRequest();
        String returnString = "";
        // 添加encry header, 告诉前端数据已加密
        response.getHeaders().add("encry", "true");
        String srcData = JSON.toJSONString(body);
        // 加密
        String encryptStr = EncryptUtil.Base64Encode(srcData.getBytes());
        log.info("接口={}, 原始数据={}, 加密后数据={}", req.getRequestURI(), srcData, encryptStr );

//        try {
//            // HttpResult是我自定义的一个类
//            HttpResult msg = (HttpResult) body;
//            //统一修改返回值/响应体
//            msg.put("sgin", "测试修改返回值");
//        }catch (Exception ex){
//
//        }
        //返回修改后的值
        // 记录日
        Method method = returnType.getMethod();
        String url=request.getURI().toASCIIString();
        log.info("{}.{},url={},result={}",method.getDeclaringClass().getSimpleName(),method.getName(),url,srcData);

        return body;
    }
}
