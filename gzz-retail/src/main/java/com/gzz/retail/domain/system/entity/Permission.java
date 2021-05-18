package com.gzz.retail.domain.system.entity;

import com.gzz.retail.infra.defines.types.OperateType;
import lombok.Data;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * 订可
 */
@Data
public class Permission {
    /**
     *  ID id
     */
    private Long id;
    /**
     *  角色ID roleId
     */
    private Long roleId;
    /**
     *  模块ID moduleId
     */
    private Long moduleId;
    /**
     *  拥有操作
     */
    private Set<OperateType> hasOperate;

    /**
     * 获取总的权限
     * @return
     */
    public int getHasPower(){
        Integer tot  = this.hasOperate.stream().map(OperateType::getKey).reduce(0, Integer::sum);
        tot  = this.hasOperate.stream().mapToInt(OperateType::getKey).sum();
        return tot;
    }
}
