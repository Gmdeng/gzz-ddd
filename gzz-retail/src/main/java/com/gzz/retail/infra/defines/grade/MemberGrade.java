package com.gzz.retail.infra.defines.grade;

/**
 * 会员级别
 */
public enum MemberGrade {
    VIP(1, "VIP"),
    GOLD(2, "黄金会员"),
    SVIP(3, "超级会员");


    private int key;
    private String name;


    MemberGrade(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public static MemberGrade valueOf(int value) {
        for (MemberGrade status : MemberGrade.values()) {
            if (status.value() == value) return status;
        }
        return VIP;
    }

    public int value() {
        return this.key;
    }

    public String label() {
        return this.name;
    }
}
