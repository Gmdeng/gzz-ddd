package com.gzz.retail.domain.member;

import com.gzz.retail.application.dto.resut.MemberVo;
import com.gzz.retail.domain.member.model.MemberEntity;

/**
 * 会员 * 领域工厂类
 */
public class MemberFactory {
    //
    public MemberEntity createMember(MemberVo userDTO ){
       //
        return new MemberEntity();
    }
}
