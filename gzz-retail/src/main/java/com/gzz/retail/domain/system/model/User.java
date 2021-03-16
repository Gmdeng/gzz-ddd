package com.gzz.retail.domain.system.model;

import com.gzz.boot.aop.exception.CustomException;
import com.gzz.core.exception.BizzException;
import com.gzz.retail.infra.defines.CommStatus;

/**
 * 系
 */
public class User {
    /**
     * ID id
     */
    private Long id;
    /**
     * 用户名 userId
     */
    private String userId;
    /**
     * 密码 passwd
     */
    private String passwd;
    /**
     * 密码盐 salt
     */
    private String salt;
    /**
     * 名称 petName
     */
    private String petName;
    /**
     * 手机号 mobile
     */
    private String mobile;
    /**
     * 允许登录IP allowIpaddr
     */
    private String allowIpaddr;
    /**
     * 备注 notes
     */
    private String notes;
    /**
     * 状态 status
     */
    private CommStatus status;

    /**
     * 登录验证
     *
     * @return
     * @throws CustomException
     */
    public boolean checkLogin() throws BizzException {
        // TODO 验证
        if (status != CommStatus.AUDIT) {
            throw new BizzException(23, "登录失败", "多年没有使用了。请重新验证");
        }
        return true;
    }

}
