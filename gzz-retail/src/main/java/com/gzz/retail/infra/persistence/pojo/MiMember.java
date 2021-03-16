package com.gzz.retail.infra.persistence.pojo;

import java.io.Serializable;
import java.util.Date;

public class MiMember implements Serializable {
    private static final long serialVersionUID = 1L;
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
    private Integer createOn;
    private String createBy;
    private Integer updateOn;
    private String updateBy;
    private String source;
    private String notes;
    private String avatar;

    public MiMember() {
    }

    public MiMember(Long id, String memberNo, String name, String nickName, String certType, String certNo, Date birthday, String sex, String mobile, String email, String address, Integer rank, Integer lvl, String type, Long joinOn, Integer status, Integer createOn, String createBy, Integer updateOn, String updateBy, String source, String notes, String avatar) {
        this.id = id;
        this.memberNo = memberNo;
        this.name = name;
        this.nickName = nickName;
        this.certType = certType;
        this.certNo = certNo;
        this.birthday = birthday;
        this.sex = sex;
        this.mobile = mobile;
        this.email = email;
        this.address = address;
        this.rank = rank;
        this.lvl = lvl;
        this.type = type;
        this.joinOn = joinOn;
        this.status = status;
        this.createOn = createOn;
        this.createBy = createBy;
        this.updateOn = updateOn;
        this.updateBy = updateBy;
        this.source = source;
        this.notes = notes;
        this.avatar = avatar;
    }

    public Long getId() {
        return id;
    }

    public String getMemberNo() {
        return memberNo;
    }

    public String getName() {
        return name;
    }

    public String getNickName() {
        return nickName;
    }

    public String getCertType() {
        return certType;
    }

    public String getCertNo() {
        return certNo;
    }

    public Date getBirthday() {
        return birthday;
    }

    public String getSex() {
        return sex;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public Integer getRank() {
        return rank;
    }

    public Integer getLvl() {
        return lvl;
    }

    public String getType() {
        return type;
    }

    public Long getJoinOn() {
        return joinOn;
    }

    public Integer getStatus() {
        return status;
    }

    public Integer getCreateOn() {
        return createOn;
    }

    public String getCreateBy() {
        return createBy;
    }

    public Integer getUpdateOn() {
        return updateOn;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getSource() {
        return source;
    }

    public String getNotes() {
        return notes;
    }

    public String getAvatar() {
        return avatar;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        MiMember other = (MiMember) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
                && (this.getMemberNo() == null ? other.getMemberNo() == null : this.getMemberNo().equals(other.getMemberNo()))
                && (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
                && (this.getNickName() == null ? other.getNickName() == null : this.getNickName().equals(other.getNickName()))
                && (this.getCertType() == null ? other.getCertType() == null : this.getCertType().equals(other.getCertType()))
                && (this.getCertNo() == null ? other.getCertNo() == null : this.getCertNo().equals(other.getCertNo()))
                && (this.getBirthday() == null ? other.getBirthday() == null : this.getBirthday().equals(other.getBirthday()))
                && (this.getSex() == null ? other.getSex() == null : this.getSex().equals(other.getSex()))
                && (this.getMobile() == null ? other.getMobile() == null : this.getMobile().equals(other.getMobile()))
                && (this.getEmail() == null ? other.getEmail() == null : this.getEmail().equals(other.getEmail()))
                && (this.getAddress() == null ? other.getAddress() == null : this.getAddress().equals(other.getAddress()))
                && (this.getRank() == null ? other.getRank() == null : this.getRank().equals(other.getRank()))
                && (this.getLvl() == null ? other.getLvl() == null : this.getLvl().equals(other.getLvl()))
                && (this.getType() == null ? other.getType() == null : this.getType().equals(other.getType()))
                && (this.getJoinOn() == null ? other.getJoinOn() == null : this.getJoinOn().equals(other.getJoinOn()))
                && (this.getStatus() == null ? other.getStatus() == null : this.getStatus().equals(other.getStatus()))
                && (this.getCreateOn() == null ? other.getCreateOn() == null : this.getCreateOn().equals(other.getCreateOn()))
                && (this.getCreateBy() == null ? other.getCreateBy() == null : this.getCreateBy().equals(other.getCreateBy()))
                && (this.getUpdateOn() == null ? other.getUpdateOn() == null : this.getUpdateOn().equals(other.getUpdateOn()))
                && (this.getUpdateBy() == null ? other.getUpdateBy() == null : this.getUpdateBy().equals(other.getUpdateBy()))
                && (this.getSource() == null ? other.getSource() == null : this.getSource().equals(other.getSource()))
                && (this.getNotes() == null ? other.getNotes() == null : this.getNotes().equals(other.getNotes()))
                && (this.getAvatar() == null ? other.getAvatar() == null : this.getAvatar().equals(other.getAvatar()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getMemberNo() == null) ? 0 : getMemberNo().hashCode());
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getNickName() == null) ? 0 : getNickName().hashCode());
        result = prime * result + ((getCertType() == null) ? 0 : getCertType().hashCode());
        result = prime * result + ((getCertNo() == null) ? 0 : getCertNo().hashCode());
        result = prime * result + ((getBirthday() == null) ? 0 : getBirthday().hashCode());
        result = prime * result + ((getSex() == null) ? 0 : getSex().hashCode());
        result = prime * result + ((getMobile() == null) ? 0 : getMobile().hashCode());
        result = prime * result + ((getEmail() == null) ? 0 : getEmail().hashCode());
        result = prime * result + ((getAddress() == null) ? 0 : getAddress().hashCode());
        result = prime * result + ((getRank() == null) ? 0 : getRank().hashCode());
        result = prime * result + ((getLvl() == null) ? 0 : getLvl().hashCode());
        result = prime * result + ((getType() == null) ? 0 : getType().hashCode());
        result = prime * result + ((getJoinOn() == null) ? 0 : getJoinOn().hashCode());
        result = prime * result + ((getStatus() == null) ? 0 : getStatus().hashCode());
        result = prime * result + ((getCreateOn() == null) ? 0 : getCreateOn().hashCode());
        result = prime * result + ((getCreateBy() == null) ? 0 : getCreateBy().hashCode());
        result = prime * result + ((getUpdateOn() == null) ? 0 : getUpdateOn().hashCode());
        result = prime * result + ((getUpdateBy() == null) ? 0 : getUpdateBy().hashCode());
        result = prime * result + ((getSource() == null) ? 0 : getSource().hashCode());
        result = prime * result + ((getNotes() == null) ? 0 : getNotes().hashCode());
        result = prime * result + ((getAvatar() == null) ? 0 : getAvatar().hashCode());
        return result;
    }
}