package com.gzz.retail.facade.api.gateway;

import com.gzz.core.response.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品
 */
@RestController
@RequestMapping("/gateway/goods")
public class GoodsApi {
    /**
     * 商品分类 列表
     *
     * @return
     */
    @GetMapping("/catalogs")
    public HttpResult getCatalogs() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 商品列表
     *
     * @return
     */
    @GetMapping("/lists")
    public HttpResult getLists() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 商品明细
     *
     * @return
     */
    @GetMapping("/detail")
    public HttpResult getDetail() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 商品可用库存
     *
     * @return
     */
    @GetMapping("/stock")
    public HttpResult getStock() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }


    /**
     * 商品skus
     *
     * @return
     */
    @GetMapping("/skus")
    public HttpResult getSkus() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }
}
