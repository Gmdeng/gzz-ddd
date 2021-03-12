package com.gzz.retail.facade.api;

import com.gzz.boot.aop.log.VisitLog;
import com.gzz.boot.aop.resubmit.ResubmitLimit;
import com.gzz.boot.event.EventPublisher;
import com.gzz.boot.shiro.jwt.JwtSecretUser;
import com.gzz.boot.shiro.jwt.JwtUtil;
import com.gzz.boot.sms.SmsTemplate;
import com.gzz.core.response.HttpResult;
import com.gzz.core.validation.ValidationResult;
import com.gzz.core.validation.ValidationUtils;
import com.gzz.retail.application.UserApp;
import com.gzz.retail.application.dto.param.LoginParam;
import com.gzz.retail.application.dto.param.RegisterParam;
import com.gzz.retail.domain.account.event.AccountChangeEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 */
@VisitLog
@RestController
@RequestMapping("/auth")
public class AuthRestApi {
    @Autowired
    private UserApp userApp;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SmsTemplate smsTemplate;

    /**
     * 登录
     *
     * @param param
     * @return
     */
    @PostMapping("/login")
    public HttpResult doLogin(LoginParam param) {
        ValidationResult err = ValidationUtils.validate(param);
        if (err.isHasError()) {
            return HttpResult.fail().data(err.getErrors());
        }
        Map<String, String> user = new HashMap<>();
        user.put("name", "Ricky");
        user.put("age", "32");
        user.put("addr", "广州天灌");
        String token = JwtUtil.createToken(new JwtSecretUser("会员姓名", "passwd"));
        /**
         * 发布领域事件
         */
        EventPublisher.publish(new AccountChangeEvent().setOrigin("测试机").setTarget("这个目标很好"));
        return HttpResult.success().data(token);
    }

    /**
     * 登录
     *
     * @param param
     * @return
     */
    @PostMapping("/register")
    public HttpResult doRegister(RegisterParam param) {
        ValidationResult err = ValidationUtils.validate(param);
        if (err.isHasError()) {
            return HttpResult.fail().data(err.getErrors());
        }


        return HttpResult.success("user");
    }

    /**
     *
     * @return
     */
    @GetMapping("/getTRedisTest")
    public HttpResult getTRedisTest() {
        redisTemplate.opsForValue().set("RickyID", UUID.randomUUID().toString());
        return HttpResult.success();
    }

    @ResubmitLimit
    @GetMapping("/getUser")
    public HttpResult getUser(String type) {

        //MemberEntity mem = userApp.getMember();
        //mem.setAddress(UUID.randomUUID().toString());
        ;//


//        if(mem!= null ){
//            throw new UnauthenticatedException("无权限进行操作3333");
//        }
        smsTemplate.sendSMS("hello");
        return HttpResult.success();
    }

    @GetMapping("/getTestUser")
    public HttpResult getTestUser() {

        Object obj = restTemplate.getForObject("http://localhost:9999/v1/auth/getUser?type=ALI", Object.class);
//        if(mem!= null ){
//            throw new UnauthenticatedException("无权限进行操作3333");
//        }
        return HttpResult.success(obj);
    }
}
