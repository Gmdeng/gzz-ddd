package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.pojo.ZRolePo;
import com.gzz.retail.infra.persistence.pojo.ZRoleAuthority;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2020-10-10 10:55:08
 */
public class ZRoleSqlProvider {

    // 选择性新增SQL
    public String insertSelective(ZRolePo zRole) {
        return new SQL() {
            {
                INSERT_INTO("z_role");
                if (zRole.getId() != null) {
                    VALUES("id", "#{id}");
                }
                if (zRole.getName() != null) {
                    VALUES("name", "#{name}");
                }
                if (zRole.getCode() != null) {
                    VALUES("code", "#{code}");
                }

                // if (zRole.getStatus() != null) {VALUES("status", "#{status}");}
                if (zRole.getUpdateOn() != null) {
                    VALUES("update_on", "#{updateOn}");
                }
                if (zRole.getUpdateBy() != null) {
                    VALUES("update_by", "#{updateBy}");
                }
                if (zRole.getCreateOn() != null) {
                    VALUES("create_on", "#{createOn}");
                }
                if (zRole.getCreateBy() != null) {
                    VALUES("create_by", "#{createBy}");
                }
                VALUES("idx", "#{idx}");
            }
        }.toString();
    }


    // 批量插入权限
    public String insertBatchAuthority(Map<String, List<ZRoleAuthority>> map) {
        List<ZRoleAuthority> list = (List<ZRoleAuthority>) map.get("list");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id}, #'{'list[{0}].roleId}, #'{'list[{0}].moduleId}, #'{'list[{0}].hasPower})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            rows.add(mf.format(new Object[]{i}));
        }
        return "INSERT INTO z_role_authority (id, role_id, module_id, has_power) VALUES "
                + String.join(", ", rows);

    }

    // 选择性更新SQL
    public String update(ZRolePo zRole) {
        return new SQL() {
            {
                UPDATE("z_role");
                if (zRole.getName() != null) {
                    SET("name=#{name}");
                }
                if (zRole.getCode() != null) {
                    SET("code=#{code}");
                }
                SET("idx=#{idx}");
                // if (zRole.getStatus() != null) {SET("status=#{status}");}
                if (zRole.getUpdateOn() != null) {
                    SET("update_on=#{updateOn}");
                }
                if (zRole.getUpdateBy() != null) {
                    SET("update_by=#{updateBy}");
                }
                if (zRole.getCreateOn() != null) {
                    SET("create_on=#{createOn}");
                }
                if (zRole.getCreateBy() != null) {
                    SET("create_by=#{createBy}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

    // 列表分页查询
    public String findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager) {
        return new SQL() {
            {
                SELECT("*");
                FROM("z_role");
                if (param.get("name") != null) WHERE("name like CONCAT('%',#{param.name},'%')");
            }
        }.toString();
    }

    // 列表查询
    public String findList(@Param("param") ParamMap param) {
        return new SQL() {
            {
                SELECT("*");
                FROM("z_role");
                if (param.get("name") != null) WHERE("name like CONCAT('%',#{param.name},'%')");

            }
        }.toString();
    }


}