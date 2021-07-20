package com.gzz.retail.infra.defines.grade;

import com.gzz.boot.mybatis.handler.IEnumPlus;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 会员级别
 */
public enum MemberGrade implements IEnumPlus {
    VIP(1, "VIP"),
    GOLD(2, "黄金会员"),
    SVIP(3, "超级会员");

    String label;
    Integer key;

    MemberGrade(int key, String label) {
        this.key = key;
        this.label = label;
    }

    public static Optional<MemberGrade> valueOf(int value) {
        return Stream.of(values()).filter(x -> x.key == value).findFirst();
    }
//    public static MemberGrade valueOf(int value) {
//        for (MemberGrade status : MemberGrade.values()) {
//            if (status.key == value) return status;
//        }
//        return VIP;
//    }


}
