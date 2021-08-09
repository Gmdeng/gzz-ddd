package com.gzz.retail.facade.api;

import com.google.common.collect.Lists;
import com.gzz.boot.aop.log.VisitLog;
import com.gzz.boot.event.EventPublisher;
import com.gzz.core.response.HttpResult;
import com.gzz.core.util.IdGenerator;
import com.gzz.retail.application.UserApp;
import com.gzz.retail.domain.account.event.AccountChangeEvent;
import com.gzz.retail.domain.account.event.AccountUpdateEvent;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "用户API", tags = {"用户操作接口"})
@RestController
@RequestMapping("/userApi")
public class UserRestApi {
    @Autowired
    private IdGenerator idGenerator;
    @Autowired
    private UserApp userApp;

    @ApiOperation(value = "用户签到", notes = "用户登录签到返回 ACCESS_TOKEN")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userid", value = "会员ID", required = true),
            @ApiImplicitParam(name = "passwd", value = "密码", required = true),
            @ApiImplicitParam(name = "nonce", value = "时间戳", required = true),
            @ApiImplicitParam(name = "sign", value = "签名串", required = true)
    })
    @VisitLog()
    @GetMapping("/info")
    public HttpResult getInfo() {

        Map<String, String> user = new HashMap<>();
        user.put("name", "Ricky");
        user.put("age", "32");
        user.put("addr", "广州天灌");

        /**
         * 发布领域事件
         */
        EventPublisher.publish(new AccountChangeEvent().setOrigin("测试机").setTarget("这个目标很好"));
        return HttpResult.success(user);
    }

    @GetMapping("/list")
    public HttpResult getList() {
        List<Map<String, String>> dataList = Lists.newArrayList();
        for (int i = 0; i < 20; i++) {
            Map<String, String> user = new HashMap<>();
            user.put("name", "Ricky" + i);
            user.put("age", "32" + i);
            user.put("addr", "广州天灌" + i);
            user.put("no", idGenerator.generateId() + "NO");
            dataList.add(user);
        }
        /**
         * 发布领域事件
         */
        EventPublisher.publish(new AccountUpdateEvent().setOrigin("列表测试机").setTarget("这个目标很好信息"));
        return HttpResult.success(dataList);
    }

}
