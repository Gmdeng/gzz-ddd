package com.gzz.retail.domain.system.primitive;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class UserId {
    private Long id;
    public UserId(Long userId){
        if(userId == null)
            throw new IllegalArgumentException("非法参数");
        this.id = userId;
    }
}
