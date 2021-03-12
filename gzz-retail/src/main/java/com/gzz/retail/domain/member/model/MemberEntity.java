package com.gzz.retail.domain.member.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Setter
@Getter
public class MemberEntity {
    private Long id;
    private String memberNo;
    private String name;
    private String nickName;
    private String certType;
    private String certNo;
    private Date birthday;
    private String sex;
    private String mobile;
    private String email;
    private String address;
    private Integer rank;
    private Integer lvl;
    private String type;
    private Long joinOn;
    private Integer status;

    private String source;
    private String notes;
    private String avatar;
}
