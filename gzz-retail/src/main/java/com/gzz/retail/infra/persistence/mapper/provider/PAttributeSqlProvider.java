package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.infra.persistence.pojo.PAttributeOptionPo;
import com.gzz.retail.infra.persistence.pojo.PAttributePo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2021-07-20 16:35:28
 */
public class PAttributeSqlProvider {

    // 选择性新增SQL insertSelective
    public String dynamicInsert(PAttributePo pAttribute) {
        return new SQL(){
            {
                INSERT_INTO("P_ATTRIBUTE");
                if (Objects.nonNull(pAttribute.getId())) VALUES("id", "#{id, jdbcType=BIGINT}");
                if (Objects.nonNull(pAttribute.getCatalogId())) VALUES("catalog_id", "#{catalogId, jdbcType=BIGINT}");
                if (Objects.nonNull(pAttribute.getName())) VALUES("name", "#{name, jdbcType=VARCHAR}");
                if (Objects.nonNull(pAttribute.getIdx())) VALUES("idx", "#{idx, jdbcType=TINYINT}");
            }
        }.toString();
    }

    // 批量插入
    public String batchInsert(@Param("dataList") List<PAttributePo> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].id}, #'{'dataList[{0}].catalogId}, #'{'dataList[{0}].name}, #'{'dataList[{0}].idx})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO P_ATTRIBUTE (id, catalog_id, name, idx) VALUES "
        + String.join(", ", rows);
    }
    // 选择性更新SQL
    public String dynamicUpdate(PAttributePo pAttribute) {
        return new SQL() {
            {
                UPDATE("P_ATTRIBUTE");
                if (Objects.nonNull(pAttribute.getCatalogId())) SET("catalog_id=#{catalogId,jdbcType=BIGINT}");
                if (Objects.nonNull(pAttribute.getName())) SET("name=#{name,jdbcType=VARCHAR}");

                if (Objects.nonNull(pAttribute.getIdx())) SET("idx=#{idx,jdbcType=TINYINT}");
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
                SELECT("a.*, c.name catalog_name");
                FROM("P_ATTRIBUTE a, P_CATALOG c");
                WHERE("a.catalog_id = c.id");
                if(StringUtil.isNotEmpty(param.get("keywords"))){
                    WHERE("a.name like CONCAT('%',#{param.keywords},'%')");
                    OR();
                    WHERE("c.name like CONCAT('%',#{param.keywords},'%')");
                }
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("a.name = #{param.name}");
            }
        }.toString();
    }

    // 批量插入选项
    public String batchInsertOptions(@Param("dataList") List<PAttributeOptionPo> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].attrId}, #'{'dataList[{0}].name}, #'{'dataList[{0}].notes})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO P_ATTRIBUTE_OPTION (attr_id, name, notes) VALUES "
                + String.join(", ", rows);
    }
}