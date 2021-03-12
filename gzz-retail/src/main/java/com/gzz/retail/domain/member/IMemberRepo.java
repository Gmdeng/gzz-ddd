package com.gzz.retail.domain.member;

import com.gzz.retail.domain.member.model.MemberEntity;

public interface IMemberRepo {
    public MemberEntity getMember(String e);
}
