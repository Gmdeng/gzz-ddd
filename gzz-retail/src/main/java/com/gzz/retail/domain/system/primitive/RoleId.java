package com.gzz.retail.domain.system.primitive;

import lombok.Data;

@Data
public class RoleId {
    private long id;
    public RoleId(Long roleId){
        if(roleId == null)
            throw new IllegalArgumentException("非法参数");
        this.id = roleId;
    }
}
