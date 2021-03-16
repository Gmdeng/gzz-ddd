package com.gzz.retail.facade.api.gateway;

import com.gzz.core.response.HttpResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章 /资讯 information
 */
@RestController
@RequestMapping("/gateway/article")
public class ArticleApi {
    /**
     * 文章分类
     *
     * @return
     */
    @GetMapping("/catalogs")
    public HttpResult getCatalogs() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 文章列表
     *
     * @return
     */
    @PostMapping("/list")
    public HttpResult getList() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 文章详细
     *
     * @return
     */
    @PostMapping("/info")
    public HttpResult getInfo() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 单页文章 Single
     *
     * @return
     */
    @PostMapping("/page")
    public HttpResult getPage() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }
}
