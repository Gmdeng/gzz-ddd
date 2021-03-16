package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.pojo.ZUser;
import com.gzz.retail.infra.persistence.pojo.ZUserRoles;
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
    public String insertSelective(ZUser zUser) {
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
    public String insertBatch(Map<String, List<ZUser>> map) {
        List<ZUser> list = (List<ZUser>) map.get("list");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id}, #'{'list[{0}].userId}, #'{'list[{0}].passwd}, #'{'list[{0}].salt}, #'{'list[{0}].petName}, #'{'list[{0}].mobile}, #'{'list[{0}].allowIpaddr}, #'{'list[{0}].notes}, #'{'list[{0}].status}, #'{'list[{0}].updateOn}, #'{'list[{0}].updateBy}, #'{'list[{0}].createOn}, #'{'list[{0}].createBy})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            rows.add(mf.format(new Object[]{i}));
        }
        return "INSERT INTO z_user (id, user_id, passwd, salt, pet_name, mobile, allow_ipaddr, notes, status, update_on, update_by, create_on, create_by) VALUES "
                + String.join(", ", rows);

    }

    // 批量插入角色
    public String insertBatchRole(Map<String, List<ZUserRoles>> map) {
        List<ZUserRoles> list = (List<ZUserRoles>) map.get("list");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id}, #'{'list[{0}].userId}, #'{'list[{0}].passwd}, #'{'list[{0}].salt}, #'{'list[{0}].petName}, #'{'list[{0}].mobile}, #'{'list[{0}].allowIpaddr}, #'{'list[{0}].notes}, #'{'list[{0}].status}, #'{'list[{0}].updateOn}, #'{'list[{0}].updateBy}, #'{'list[{0}].createOn}, #'{'list[{0}].createBy})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            rows.add(mf.format(new Object[]{i}));
        }
        return "INSERT INTO z_user (id, user_id, passwd, salt, pet_name, mobile, allow_ipaddr, notes, status, update_on, update_by, create_on, create_by) VALUES "
                + String.join(", ", rows);

    }

    // 选择性更新SQL
    public String update(ZUser zUser) {
        return new SQL() {
            {
                UPDATE("z_user");
                if (zUser.getUserId() != null) {
                    SET("user_id=#{userId}");
                }
                if (zUser.getPasswd() != null) {
                    SET("passwd=#{passwd}");
                }
                if (zUser.getSalt() != null) {
                    SET("salt=#{salt}");
                }
                if (zUser.getPetName() != null) {
                    SET("pet_name=#{petName}");
                }
                if (zUser.getMobile() != null) {
                    SET("mobile=#{mobile}");
                }
                if (zUser.getAllowIpaddr() != null) {
                    SET("allow_ipaddr=#{allowIpaddr}");
                }
                if (zUser.getNotes() != null) {
                    SET("notes=#{notes}");
                }
                if (zUser.getUpdateOn() != null) {
                    SET("update_on=#{updateOn}");
                }
                if (zUser.getUpdateBy() != null) {
                    SET("update_by=#{updateBy}");
                }
                SET("status=#{status}");
                WHERE("id=#{id}");
            }
        }.toString();
    }

    // 列表分页查询
    public String findListByPage(ParamMap param, Pager pager) {
        return new SQL() {
            {
                SELECT("*");
                FROM("z_user");
                if (param.get("name") != null) WHERE("name like CONCAT('%',#{param.name},'%')");
            }
        }.toString();
    }

    // 列表查询
    public String findList(ParamMap param) {
        return new SQL() {
            {
                SELECT("*");
                FROM("z_user");
                if (param.get("name") != null) WHERE("name like CONCAT('%',#{param.name},'%')");

            }
        }.toString();
    }


}