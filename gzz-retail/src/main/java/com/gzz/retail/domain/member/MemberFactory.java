package com.gzz.retail.domain.member;

import com.gzz.retail.domain.member.model.MemberEntity;

/**
 * 会员 * 领域工厂类
 */
public class MemberFactory {
    //
    public MemberEntity createMember() {
        //
        return new MemberEntity();
    }
}
