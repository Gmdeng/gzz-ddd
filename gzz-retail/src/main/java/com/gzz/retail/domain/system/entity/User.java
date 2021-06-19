package com.gzz.retail.domain.system.entity;

import com.gzz.boot.aop.exception.CustomException;
import com.gzz.core.exception.BizzException;
import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.domain.system.primitive.UserId;
import com.gzz.retail.infra.defines.CommStatus;
import lombok.Data;

import java.util.Set;

/**
 * 系
 */
@Data
public class User {
    /**
     * 用户名 userId
     */
    private UserId userId;
    /**
     * 名称 petName
     */
    private String petName;

    /**
     * 密码 passwd
     */
    private String passwd;

    /**
     * 密码盐 salt
     */
    private String salt;

    /**
     * 手机号 mobile
     */
    private String mobile;
    /**
     * 邮箱 eamil
     */
    private String email;

    /**
     * 允许登录IP allowIpaddr
     */
    private String allowIpaddr;
    /**
     * 拒绝登录IP denyIpaddr
     */
    private String denyIpaddr;
    /**
     * 状态 status
     */
    private CommStatus status;

    /**
     * 会
     */
    private Set<RoleId> hasRoles;
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

    /**
     * 变更密码
     * @param passwdtext
     */
    public void changePassword(String passwdtext) {
        this.passwd =  passwdtext;
    }
}
