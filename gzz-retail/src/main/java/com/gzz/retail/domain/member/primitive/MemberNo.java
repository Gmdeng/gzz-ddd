package com.gzz.retail.domain.member.primitive;

import lombok.Data;

@Data
public class MemberNo {
    private String no;
    public MemberNo(String memberNo){
        this.no = memberNo;
    }
}
