package com.gzz.retail.facade.api.admin;

import com.gzz.core.response.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车
 */
@RestController
@RequestMapping("/admin/shoppingCart")
public class ShoppingCartApi {
    /**
     * 列表
     *
     * @return
     */
    @GetMapping("/list")
    public HttpResult getList() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 添加
     *
     * @return
     */
    @PostMapping("/add")
    public HttpResult doAdd() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 修改
     *
     * @return
     */
    @PostMapping("/modify")
    public HttpResult doModify() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 移出
     *
     * @return
     */
    @GetMapping("/remove")
    public HttpResult doRemove() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 选中
     *
     * @return
     */
    @GetMapping("/choose")
    public HttpResult doChoose() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 不选中
     *
     * @return
     */
    @GetMapping("/unChoose")
    public HttpResult doUnChoose() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }
}
