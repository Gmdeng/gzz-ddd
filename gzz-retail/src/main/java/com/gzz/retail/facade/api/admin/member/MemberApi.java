package com.gzz.retail.facade.api.admin.member;

import com.gzz.core.response.HttpResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 会员
 */
@RestController
@RequestMapping("/admin/member")
public class MemberApi {
    /**
     * 获取个人信息
     *
     * @return
     */
    @PostMapping("/profile")
    public HttpResult getProfile() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 修改个人信息
     *
     * @return
     */
    @PostMapping("/modifyProfile")
    public HttpResult modifyProfile() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

    /**
     * 修改个人信息
     *
     * @return
     */
    @PostMapping("/modifyPassword")
    public HttpResult modifyPassword() {
        return HttpResult.fail("时间" + System.currentTimeMillis());
    }

}
