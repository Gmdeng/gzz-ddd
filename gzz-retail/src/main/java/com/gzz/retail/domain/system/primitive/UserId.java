package com.gzz.retail.domain.system.primitive;

import com.gzz.core.util.StringUtil;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserId {
    private Long id;
    private String name;
    public UserId(Long userId){
        if(userId == null)
            throw new IllegalArgumentException("用户ID不能为空");
        this.id = userId;
    }
    public UserId(String name){
        if(StringUtil.isEmpty(name))
            throw new IllegalArgumentException("用户名不能为空");
        this.name = name;
    }
    public UserId(Long userId, String name){
        this.id = userId;
        this.name = name;
    }
}
