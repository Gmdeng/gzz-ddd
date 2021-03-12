package com.gzz.retail.facade.api.admin;

import com.gzz.core.response.HttpResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *  授权认证相关
 */
@RestController
@RequestMapping("/admin/authentication")
public class AuthenticationApi {

    /**
     *  登录
     * @return
     */
    @PostMapping("/login")
    public HttpResult doLogin() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     *  注册
     * @return
     */
    @PostMapping("/register")
    public HttpResult doRegister() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     *  找回密码
     * @return
     */
    @PostMapping("/findPasswd")
    public HttpResult doFindPasswd() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

}
