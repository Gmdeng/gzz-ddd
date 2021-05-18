package com.gzz.retail.application.assembler.dto;

import com.gzz.retail.infra.defines.types.OperateType;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 许可
 */
@Data
public class PermissionNodeDto {
    private String title;
    private Long moduleId;
    private List<OperateType> operates;
    private List<OperateType> hasPower;
    private List<PermissionNodeDto>  children = new ArrayList<>();

    public PermissionNodeDto(String title, Long moduleId){
        this.title = title;
        this.moduleId = moduleId;
    }
}
