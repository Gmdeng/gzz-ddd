package com.gzz.retail.facade.api;

import com.gzz.boot.aop.log.VisitLog;
import com.gzz.core.exception.BizzException;
import com.gzz.core.response.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@VisitLog
@RestController
@RequestMapping("/account")
public class AccountRestApi {
    @GetMapping("/info")
    public HttpResult getInfo() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }


    @GetMapping("/info2")
    public HttpResult getInfo2(String name) throws Exception {
        if ("admin".equalsIgnoreCase(name)) {
            throw new Exception("测试异常处理。。。。。");
        }
        return HttpResult.success("时间" + System.currentTimeMillis() + "  _" + name);
    }

    /**
     * 全局异常处理 测试
     * @param num
     * @return
     */
    @GetMapping("/testGlobalException")
    public String testGlobalException(Integer num, String name) throws BizzException {
        // TODO 演示需要，实际上参数是否为空通过 @RequestParam(required = true)  就可以控制
        if (num == null ) {
            // throw new BusinessException(400, "num不能为空");
        }
        int i = 10 / num;
        return name+ "result:" + i;
    }
}
