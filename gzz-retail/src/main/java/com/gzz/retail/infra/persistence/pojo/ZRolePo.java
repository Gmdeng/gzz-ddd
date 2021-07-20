package com.gzz.retail.infra.persistence.pojo;

import com.gzz.boot.mybatis.autofill.AutoFillTime;
import com.gzz.boot.mybatis.autofill.AutoFillUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色 实体类
 *
 * @author Auto-Builder
 * @CrateOn 2020-10-10 10:55:08
 */
@Data
public class ZRolePo implements Serializable {
    /**
     * ID id
     */
    private Long id;
    /**
     * 名称 name
     */
    private String name;
    /**
     * 编码（唯一的，java类名） code
     */
    private String code;

    /**
     * 排序 idx
     */
    private int idx;

    /**
     * 状态 status
     */
    private int status;

    /**
     *  备注 notes
     */
    private String notes;

    /**
     * 更新时间 updateOn
     */
    @AutoFillTime
    private Date updateOn;

    /**
     * 更新人 updateBy
     */
    @AutoFillUser
    private String updateBy;

    /**
     * 创建时间 createOn
     */
    @AutoFillTime
    private Date createOn;

    /**
     * 创建人 createBy
     */
    @AutoFillUser
    private String createBy;
}