package com.gzz.core.request;

import com.gzz.core.util.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 请求参数，解密
 * 请求body全局统一处理
 */
@Component
@ControllerAdvice
@Slf4j
public class GlobalRequestBodyAdvice implements RequestBodyAdvice {
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.getMethod().isAnnotationPresent(SecurityParam.class);
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return inputMessage;
        //重购HttpInputMessage类。
//        return new HttpInputMessage() {
//            @Override
//            public InputStream getBody() throws IOException {
//                log.info("此处进行解密数据");
//                return new ByteArrayInputStream(IOUtils.toString(inputMessage.getBody()).replace("-encrypt", "").getBytes(StandardCharsets.UTF_8));
//            }
//
//            @Override
//            public HttpHeaders getHeaders() {
//                return inputMessage.getHeaders();
//            }
//        };
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        String dealData = null;
        Map<String, String> dataMap =(Map)body;
        String srcData = dataMap.get("data");
        // 解密操作
        dealData = new String(EncryptUtil.Base64Decode(srcData));
        return dealData;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        log.info("空请求内容。。。。");
        return body;
    }
}
