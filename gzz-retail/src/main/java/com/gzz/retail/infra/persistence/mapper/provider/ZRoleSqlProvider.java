package com.gzz.retail.infra.persistence.mapper.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.ArrayList;

import com.gzz.retail.infra.persistence.pojo.ZRolePermissionPo;
import com.gzz.retail.infra.persistence.pojo.ZRolePo;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.annotations.Param;
import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;


/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2021-05-18 10:52:36
 */
public class ZRoleSqlProvider {

    // 选择性新增SQL insertSelective
    public String dynamicInsert(ZRolePo zRole) {
        return new SQL(){
            {
                INSERT_INTO("Z_ROLE");
                if (!StringUtil.isNull(zRole.getId())) {VALUES("id", "#{id, jdbcType=BIGINT}");}
                if (!StringUtil.isNull(zRole.getName())) {VALUES("name", "#{name, jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zRole.getCode())) {VALUES("code", "#{code, jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zRole.getIdx())) {VALUES("idx", "#{idx, jdbcType=TINYINT}");}
                if (!StringUtil.isNull(zRole.getStatus())) {VALUES("status", "#{status, jdbcType=TINYINT}");}
                if (!StringUtil.isNull(zRole.getUpdateOn())) {VALUES("update_on", "#{updateOn, jdbcType=TIMESTAMP}");}
                if (!StringUtil.isNull(zRole.getUpdateBy())) {VALUES("update_by", "#{updateBy, jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zRole.getCreateOn())) {VALUES("create_on", "#{createOn, jdbcType=TIMESTAMP}");}
                if (!StringUtil.isNull(zRole.getCreateBy())) {VALUES("create_by", "#{createBy, jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zRole.getNotes())) {VALUES("notes", "#{notes, jdbcType=VARCHAR}");}
            }
        }.toString();
    }

    // 批量插入
    public String batchInsert(@Param("dataList") List<ZRolePo> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].id}, #'{'dataList[{0}].name}, #'{'dataList[{0}].code}, #'{'dataList[{0}].idx}, #'{'dataList[{0}].status}, #'{'dataList[{0}].updateOn}, #'{'dataList[{0}].updateBy}, #'{'dataList[{0}].createOn}, #'{'dataList[{0}].createBy}, #'{'dataList[{0}].notes})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO Z_ROLE (id, name, code, idx, status, update_on, update_by, create_on, create_by, notes) VALUES "
                + String.join(", ", rows);
    }
    // 选择性更新SQL
    public String dynamicUpdate(ZRolePo zRole) {
        return new SQL() {
            {
                UPDATE("Z_ROLE");
                if (!StringUtil.isNull(zRole.getName())) {SET("name=#{name,jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zRole.getCode())) {SET("code=#{code,jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zRole.getIdx())) {SET("idx=#{idx,jdbcType=TINYINT}");}
                if (!StringUtil.isNull(zRole.getStatus())) {SET("status=#{status,jdbcType=TINYINT}");}
                if (!StringUtil.isNull(zRole.getUpdateOn())) {SET("update_on=#{updateOn,jdbcType=TIMESTAMP}");}
                if (!StringUtil.isNull(zRole.getUpdateBy())) {SET("update_by=#{updateBy,jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zRole.getCreateOn())) {SET("create_on=#{createOn,jdbcType=TIMESTAMP}");}
                if (!StringUtil.isNull(zRole.getCreateBy())) {SET("create_by=#{createBy,jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zRole.getNotes())) {SET("notes=#{notes,jdbcType=VARCHAR}");}
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
                FROM("Z_ROLE");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name like CONCAT('%',#{param.name},'%')");
                if(StringUtil.isNotEmpty(param.get("code"))) WHERE("name = #{param.code}");
            }
        }.toString();
    }

    // 批量插入许可
    // @Param("dataList") 必须两个地方要一样
    public String batchInsertPermission(@Param("dataList") List<ZRolePermissionPo> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].roleId}, #'{'dataList[{0}].moduleId}, #'{'dataList[{0}].hasPower})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO Z_ROLE_PERMISSION (role_id, module_id, has_power) VALUES "
                + String.join(", ", rows);
    }
}