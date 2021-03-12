package com.gzz.retail.facade.api.gateway;

import com.gzz.core.response.HttpResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 评论
 */
@RestController
@RequestMapping("/gateway/comment")
public class CommentApi {

    /**
     *  评论列表
     * @return
     */
    @PostMapping("/list")
    public HttpResult getList() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     *  增加评论
     * @return
     */
    @PostMapping("/add")
    public HttpResult doAdd() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     *  删除评论
     * @return
     */
    @PostMapping("/remove")
    public HttpResult doRemove() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }
}
