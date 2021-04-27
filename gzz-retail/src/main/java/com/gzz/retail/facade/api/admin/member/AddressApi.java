package com.gzz.retail.facade.api.admin.member;

import com.gzz.core.response.HttpResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 收货地址信息
 */
@RestController
@RequestMapping("/admin/address")
public class AddressApi {
    /**
     * 地址列表
     *
     * @return
     */
    @PostMapping("/list")
    public HttpResult getList() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 地址详细
     *
     * @return
     */
    @PostMapping("/info")
    public HttpResult getInfo() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 新增/修改地址
     *
     * @return
     */
    @PostMapping("/modify")
    public HttpResult doModify() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 设置默认
     *
     * @return
     */
    @PostMapping("/default")
    public HttpResult setDefault() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }
}
