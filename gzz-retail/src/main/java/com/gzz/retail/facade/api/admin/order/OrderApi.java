package com.gzz.retail.facade.api.admin.order;

import com.gzz.core.response.HttpResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 订单
 */

@RestController
@RequestMapping("/admin/order")
public class OrderApi {

    /**
     * 下订单
     *
     * @return
     */
    @PostMapping("/make")
    public HttpResult doMake() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 提交订单
     *
     * @return
     */
    @PostMapping("/submit")
    public HttpResult doSubmit() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 取消订单
     *
     * @return
     */
    @PostMapping("/cancel")
    public HttpResult doCancel() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 订单确认收货(recepet)
     *
     * @return
     */
    @PostMapping("/receive")
    public HttpResult doReceive() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 我的订单
     *
     * @return
     */
    @PostMapping("/myOrders")
    public HttpResult getMyOrders() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

}
