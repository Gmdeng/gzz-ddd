package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.pojo.ZUserPo;
import com.gzz.retail.infra.persistence.pojo.ZUserRoles;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2020-10-10 10:55:57
 */
public class ZUserSqlProvider {

    // 选择性新增SQL
    public String insertSelective(ZUserPo zUser) {
        return new SQL() {
            {
                INSERT_INTO("z_user");
                if (zUser.getId() != null) {
                    VALUES("id", "#{id}");
                }
                if (zUser.getUserId() != null) {
                    VALUES("user_id", "#{userId}");
                }
                if (zUser.getPasswd() != null) {
                    VALUES("passwd", "#{passwd}");
                }
                if (zUser.getSalt() != null) {
                    VALUES("salt", "#{salt}");
                }
                if (zUser.getPetName() != null) {
                    VALUES("pet_name", "#{petName}");
                }
                if (zUser.getMobile() != null) {
                    VALUES("mobile", "#{mobile}");
                }
                if (zUser.getAllowIpaddr() != null) {
                    VALUES("allow_ipaddr", "#{allowIpaddr}");
                }
                if (zUser.getNotes() != null) {
                    VALUES("notes", "#{notes}");
                }
                //if (zUser.getStatus() != null) {VALUES("status", "#{status}");}
                if (zUser.getUpdateOn() != null) {
                    VALUES("update_on", "#{updateOn}");
                }
                if (zUser.getUpdateBy() != null) {
                    VALUES("update_by", "#{updateBy}");
                }
                if (zUser.getCreateOn() != null) {
                    VALUES("create_on", "#{createOn}");
                }
                if (zUser.getCreateBy() != null) {
                    VALUES("create_by", "#{createBy}");
                }
                VALUES("status", "#{status}");
            }
        }.toString();
    }

    // 批量插入
    public String insertBatch(Map<String, List<ZUserPo>> map) {
        List<ZUserPo> list = (List<ZUserPo>) map.get("list");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id}, #'{'list[{0}].userId}, #'{'list[{0}].passwd}, #'{'list[{0}].salt}, #'{'list[{0}].petName}, #'{'list[{0}].mobile}, #'{'list[{0}].allowIpaddr}, #'{'list[{0}].notes}, #'{'list[{0}].status}, #'{'list[{0}].updateOn}, #'{'list[{0}].updateBy}, #'{'list[{0}].createOn}, #'{'list[{0}].createBy})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            rows.add(mf.format(new Object[]{i}));
        }
        return "INSERT INTO z_user (id, user_id, passwd, salt, pet_name, mobile, allow_ipaddr, notes, status, update_on, update_by, create_on, create_by) VALUES "
                + String.join(", ", rows);

    }

    // 批量插入角色
    public String insertBatchRole(@Param("dataList") List<ZUserRoles> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].userId}, #'{'dataList[{0}].roleId},  #'{'dataList[{0}].enable})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[]{i}));
        }
        return "INSERT INTO z_user (user_id, role_id, enable) VALUES "
                + String.join(", ", rows);

    }

    // 选择性更新SQL
    public String update(ZUserPo zUser) {
        return new SQL() {
            {
                UPDATE("z_user");
                if (zUser.getUserId() != null) {SET("user_id=#{userId, jdbcType=VARCHAR}");}
                if (zUser.getPasswd() != null) {SET("passwd=#{passwd, jdbcType=VARCHAR}");}
                if (zUser.getSalt() != null) { SET("salt=#{salt, jdbcType=VARCHAR}");}
                if (zUser.getPetName() != null) {SET("pet_name=#{petName, jdbcType=VARCHAR}");}
                if (zUser.getMobile() != null) {SET("mobile=#{mobile, jdbcType=VARCHAR}");}
                if (zUser.getAllowIpaddr() != null) {SET("allow_ipaddr=#{allowIpaddr, jdbcType=VARCHAR}");}
                if (zUser.getNotes() != null) { SET("notes=#{notes, jdbcType=VARCHAR}");}
                if (zUser.getUpdateOn() != null) { SET("update_on=#{updateOn, jdbcType=TIMESTAMP}"); }
                if (zUser.getUpdateBy() != null) { SET("update_by=#{updateBy, jdbcType=VARCHAR}"); }
                WHERE("id=#{id}");
            }
        }.toString();
    }

    // 列表分页查询
    public String findListByPage(@Param("param")ParamMap param, @Param("pager") Pager pager) {
        return findList(param);
    }

    // 列表查询
    public String findList(@Param("param")ParamMap param) {
        return new SQL() {
            {
                SELECT("*");
                FROM("z_user");
                if (param.get("userId") != null) WHERE("user_id =#{param.userId}");
                if (param.get("mobile") != null) WHERE("mobile =#{param.mobile}");
                if (param.get("email") != null) WHERE("email like CONCAT('%',#{param.email},'%')");
                if (param.get("petName") != null) WHERE("pet_name like CONCAT('%',#{param.petName},'%')");
                if (param.get("name") != null) WHERE("name like CONCAT('%',#{param.name},'%')");

            }
        }.toString();
    }


}