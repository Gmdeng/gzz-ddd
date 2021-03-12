package com.gzz.boot.shiro.jwt;

import com.alibaba.fastjson.JSON;
import com.gzz.core.response.HttpResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * BasicHttpAuthenticationFilter继承 AuthenticatingFilter 过滤器
 * 其能够自动地进行基于所述传入请求的认证尝试。
 * BasicHttpAuthenticationFilter 基本访问认证过滤器
 * 此实现是每个基本HTTP身份验证规范的Java实现
 * 通过此过滤器得到HTTP请求资源获取Authorization传递过来的token参数
 * 获取subject对象进行身份验证
 */
@Slf4j
public class JwtAuthFilter extends BasicHttpAuthenticationFilter {
    private static final String AUTHZ_HEADER = "AuthorizationToken";
    private static final String CHARSET = "UTF-8";


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("访问地址。。。。。" + req.getRequestURL());
        log.info("访问IP地址。。。。" + req.getRemoteAddr());
        boolean loggedIn = false;
        // 已登录用户
        if (isLoginAttempt(request, response)) {
            log.info("拒绝访问。。。登录验证。");
            loggedIn = executeLogin(request, response);
        }
        if (!loggedIn) {
            sendChallenge(request, response);
        }
        return loggedIn;
    }

    /**
     * 请求是否已经登录（携带token）
     * 判断用户是否是登入,检测headers里是否包含token字段
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String authzHeader = WebUtils.toHttp(request).getHeader(AUTHZ_HEADER);
        return authzHeader != null;
    }

    /**
     * 构建未授权的请求返回,filter层的异常不受exceptionAdvice控制,
     * 这里返回401,把返回的json丢到response中
     */
    @Override
    protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
        log.info("禁止访问。直接返回JSON。。。");
        HttpServletResponse httpResponse = WebUtils.toHttp(response);
        String contentType = "application/json;charset=" + CHARSET;
        httpResponse.setStatus(200);
        httpResponse.setContentType(contentType);
        try {

            String msg = "对不起,您无权限进行操作!";
            PrintWriter printWriter = httpResponse.getWriter();
            printWriter.append(JSON.toJSONString(HttpResult.fail(msg)));
            printWriter.flush();
            //httpResponse.flushBuffer();
        } catch (IOException e) {
            log.error("sendChallenge error,can not resolve httpServletResponse");
        }
        return false;
    }

    /**
     * Authorization携带的参数为token
     * JwtToken实现了AuthenticationToken接口封装了token参数
     * 通过getSubject方法获取 subject对象
     * login()发送身份验证
     * <p>
     * 为什么需要在Filter中调用login,不能在controller中调用login?
     * 由于Shiro默认的验证方式是基于session的，在基于token验证的方式中，不能依赖session做为登录的判断依据．
     *
     * @param request
     * @param response
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        log.info("访问验证处理");
        try {
            String token = WebUtils.toHttp(request).getHeader(AUTHZ_HEADER);
            if (null == token) {
                String msg = "executeLogin method token must not be null";
                throw new IllegalStateException(msg);
            }
            //交给realm判断是否有权限,没权限返回false交给onAccessDenied
            JwtToken jwtToken = new JwtToken(token);
            // 提交给realm进行登入，如果错误会抛出异常并被捕获
            Subject subject = getSubject(request, response);
            subject.login(jwtToken);
            log.info("JWT验证用户信息成功");
            // 如果没有抛出异常则代表登入成功，返回true
            return true;
        } catch (Exception e) {
            /* *
             *  原生的shiro验证失败会进入全局异常 但是 和JWT结合以后却不进入了  之前一直想不通
             *  原因是 JWT直接在过滤器里验证  验证成功与否 都是直接返回到过滤器中 成功在进入controller
             *  失败直接返回进入springboot自定义异常处理页面
             */
            log.error("登录处理：" +e.getMessage());
        }
        return false;
    }


    /**
     * 访前处理
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        log.info("访问前置处理。。。。");
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpResponse.setHeader("Access-control-Allow-Origin", httpRequest.getHeader("Origin"));
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        httpResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpResponse.setHeader("Access-Control-Allow-Headers", httpRequest.getHeader("Access-Control-Request-Headers"));

        httpResponse.setHeader("Access-Control-Allow-Headers", "authorization, content-type, Cookie");
        httpResponse.setHeader("Access-Control-Expose-Headers", "X-forwared-port, X-forwarded-host");
        httpResponse.setHeader("Access-Control-expose-Headers", "Authorization, BiToken, Cookie");

        if (httpResponse.getHeader(HttpHeaders.ORIGIN) != null) {
            /*
            //允许所有域名的脚本访问该资源
            httpResponse.setHeader("Access-Control-Allow-Origin", "*");
            //表明服务器允许请求中携带字段 ，指明了实际请求中允许携带的首部字段
            httpResponse.setHeader("Access-Control-Allow-Headers",
                    "User-Agent,Origin,Cache-Control,Content-type,Date,Server,withCredentials,AccessToken");
            //指定了当浏览器的credentials设置为true时是否允许浏览器读取response的内容
            httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
            //表明服务器允许客户端使用 POST, GET等 方法发起请求
            httpResponse.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
            //表明该响应的有效时间为 86400 秒 指定了preflight请求的结果能够被缓存多久
            httpResponse.setHeader("Access-Control-Max-Age", "86400");
            //与“Access-Control-Allow-Headers”相反，表示不支持的头信息字段
            httpResponse.setHeader("Access-Control-Expose-Headers", "accesstoken");
            httpResponse.setHeader("Access-Control-Request-Headers", "accesstoken");
            //告诉浏览器上缓冲存储的页，立即过期还有多少时间
            httpResponse.setHeader("Expires", "-1");
            //告诉浏览器当前页面不进行缓存，每次访问的时间必须从服务器上读取最新的数据
            httpResponse.setHeader("Cache-Control", "no-cache");
            httpResponse.setHeader("pragma", "no-cache");
             */
        }
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        // 跨域时,option请求直接返回正常状态
        if (httpRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 为response设置header，实现跨域
     */
    private void setHeader(HttpServletRequest request,HttpServletResponse response){
        //跨域的header设置
        response.setHeader("Access-control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getMethod());
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        //防止乱码，适用于传输JSON数据
        //Content-Type, Content-Length, Authorization, Accept, X-Requested-With , yourHeaderFeild
        response.setHeader("Content-Type","application/json;charset=UTF-8");
        response.setStatus(HttpStatus.OK.value());
    }
}
