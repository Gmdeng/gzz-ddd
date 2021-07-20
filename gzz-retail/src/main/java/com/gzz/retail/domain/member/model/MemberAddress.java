package com.gzz.retail.domain.member.model;

import lombok.Data;

/**
 * 会员地址
 */
@Data
public class MemberAddress {
    private Long id;
    // 会员编号
    private String memberNo;
    // 地区编码
    private String areaCode;
    // 地址
    private String address;
    // 邮编号码
    private String zipCode;
    // 手机号
    private String mobile;
    // 电话
    private String tel;
    // 电子邮件
    private String email;
    // 是否为默认
    private boolean isDefault;
}
