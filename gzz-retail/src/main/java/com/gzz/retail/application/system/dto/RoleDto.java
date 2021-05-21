package com.gzz.retail.application.system.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gzz.retail.domain.system.entity.Permission;
import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.infra.defines.CommStatus;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleDto {
    /**
     * ID id
     */
    private Long roleId;

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
     * 排序 idx
     */
    private String notes;

    /**
     * 状态 status
     */
    private CommStatus status;

    /**
     * 更新时间 updateOn
     */
    private Date updateOn;

    /**
     * 更新人 updateBy
     */
    private String updateBy;

    /**
     * 创建时间 createOn
     */

    @JsonFormat(pattern="yyyy年MM月dd日 HH:mm:ss",timezone = "GMT+8")
    private Date createOn;

    /**
     * 创建人 createBy
     */
    private String createBy;
}
