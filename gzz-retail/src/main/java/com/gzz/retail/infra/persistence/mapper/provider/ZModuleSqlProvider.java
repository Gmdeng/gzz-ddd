package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.infra.persistence.pojo.ZModule;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;


/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2021-02-26 11:45:48
 */
public class ZModuleSqlProvider {

    // 选择性新增SQL insertSelective
    public String dynamicInsert(ZModule zModule) {
        return new SQL(){
            {
                INSERT_INTO("Z_MODULE");
                if (!StringUtil.isNull(zModule.getId())) {VALUES("id", "#{id, jdbcType=BIGINT}");}
                if (!StringUtil.isNull(zModule.getParentId())) {VALUES("parent_id", "#{parentId, jdbcType=BIGINT}");}
                if (!StringUtil.isNull(zModule.getType())) {VALUES("type", "#{type, jdbcType=CHAR}");}
                if (!StringUtil.isNull(zModule.getName())) {VALUES("name", "#{name, jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zModule.getCode())) {VALUES("code", "#{code, jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zModule.getOperate())) {VALUES("operate", "#{operate, jdbcType=TINYINT}");}
                if (!StringUtil.isNull(zModule.getIcon())) {VALUES("icon", "#{icon, jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zModule.getUrl())) {VALUES("url", "#{url, jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zModule.getIdx())) {VALUES("idx", "#{idx, jdbcType=TINYINT}");}
                if (!StringUtil.isNull(zModule.getStatus())) {VALUES("status", "#{status, jdbcType=TINYINT}");}
                if (!StringUtil.isNull(zModule.getUpdateOn())) {VALUES("update_on", "#{updateOn, jdbcType=TIMESTAMP}");}
                if (!StringUtil.isNull(zModule.getUpdateBy())) {VALUES("update_by", "#{updateBy, jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zModule.getCreateOn())) {VALUES("create_on", "#{createOn, jdbcType=TIMESTAMP}");}
                if (!StringUtil.isNull(zModule.getCreateBy())) {VALUES("create_by", "#{createBy, jdbcType=VARCHAR}");}
            }
        }.toString();
    }

    // 批量插入
    public String batchInsert(@Param("dataList") List<ZModule> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].id}, #'{'dataList[{0}].parentId}, #'{'dataList[{0}].type}, #'{'dataList[{0}].name}, #'{'dataList[{0}].code}, #'{'dataList[{0}].operate}, #'{'dataList[{0}].icon}, #'{'dataList[{0}].url}, #'{'dataList[{0}].idx}, #'{'dataList[{0}].status}, #'{'dataList[{0}].updateOn}, #'{'dataList[{0}].updateBy}, #'{'dataList[{0}].createOn}, #'{'dataList[{0}].createBy})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO Z_MODULE (id, parent_id, type, name, code, operate, icon, url, idx, status, update_on, update_by, create_on, create_by) VALUES "
                + String.join(", ", rows);
    }
    // 选择性更新SQL
    public String dynamicUpdate(ZModule zModule) {
        return new SQL() {
            {
                UPDATE("Z_MODULE");
                if (!StringUtil.isNull(zModule.getParentId())) {SET("parent_id=#{parentId,jdbcType=BIGINT}");}
                if (!StringUtil.isNull(zModule.getType())) {SET("type=#{type,jdbcType=CHAR}");}
                if (!StringUtil.isNull(zModule.getName())) {SET("name=#{name,jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zModule.getCode())) {SET("code=#{code,jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zModule.getOperate())) {SET("operate=#{operate,jdbcType=TINYINT}");}
                if (!StringUtil.isNull(zModule.getIcon())) {SET("icon=#{icon,jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zModule.getUrl())) {SET("url=#{url,jdbcType=VARCHAR}");}
                if (!StringUtil.isNull(zModule.getIdx())) {SET("idx=#{idx,jdbcType=TINYINT}");}
                if (!StringUtil.isNull(zModule.getStatus())) {SET("status=#{status,jdbcType=TINYINT}");}
                if (!StringUtil.isNull(zModule.getUpdateOn())) {SET("update_on=#{updateOn,jdbcType=TIMESTAMP}");}
                if (!StringUtil.isNull(zModule.getUpdateBy())) {SET("update_by=#{updateBy,jdbcType=VARCHAR}");}
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
                FROM("Z_MODULE");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name like CONCAT('%',#{param.name},'%')");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name = #{param.name}");
                ORDER_BY("idx desc, parent_id");
            }
        }.toString();
    }


}