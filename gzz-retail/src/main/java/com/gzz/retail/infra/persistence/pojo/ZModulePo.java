package com.gzz.retail.infra.persistence.pojo;

import com.gzz.boot.mybatis.autofill.AutoFillTime;
import com.gzz.boot.mybatis.autofill.AutoFillUser;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 模块 实体类
 *
 * @author Auto-Builder
 * @CrateOn 2021-2-26 11:45:48
 */
@Data
public class ZModulePo implements Serializable {
    /**
     * ID id
     */
    private Long id;
    /**
     * 父模块ID parentId
     */
    private Long parentId;
    /**
     * 类型 type
     */
    private String type;
    /**
     * 名称 name
     */
    private String name;
    /**
     * 编码（唯一的，java类名） code
     */
    private String code;
    /**
     * 操作 operate
     */
    private Integer operate;
    /**
     * 图标 icon
     */
    private String icon;
    /**
     * 链接地址 url
     */
    private String url;
    /**
     * 排序 idx
     */
    private int idx;
    /**
     * 状态 status
     */
    private int status;
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