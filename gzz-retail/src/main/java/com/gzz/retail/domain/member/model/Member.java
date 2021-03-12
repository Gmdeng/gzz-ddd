package com.gzz.retail.domain.member.model;

import com.gzz.retail.infra.defines.grade.MemberGrade;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 会员实体类
 */
@Data
@Setter
@Getter
public class Member {
    private Long id;
    // 会员编号
    private String no;
    // 头像
    private String avatar;
    // 姓名
    private String name;
    // 性别
    private String sex;
    // 昵称
    private String petName;
    // 级别
    private MemberGrade grade;
    // 加入时间
    private Long joinOn;
    // 状态
    private Integer status;
    // 备注
    private String notes;
    private MemeberExtend extendInfo;

}
