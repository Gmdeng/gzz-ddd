package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.infra.persistence.pojo.PSpecificationGroupPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2021-07-20 16:37:20
 */
public class PSpecificationGroupSqlProvider {

    // 选择性新增SQL insertSelective
    public String dynamicInsert(PSpecificationGroupPo pSpecificationGroup) {
        return new SQL(){
            {
                INSERT_INTO("P_SPECIFICATION_GROUP");
                if (Objects.nonNull(pSpecificationGroup.getId())) VALUES("id", "#{id, jdbcType=BIGINT}");
                if (Objects.nonNull(pSpecificationGroup.getName())) VALUES("name", "#{name, jdbcType=VARCHAR}");
            }
        }.toString();
    }

    // 批量插入
    public String batchInsert(@Param("dataList") List<PSpecificationGroupPo> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].id}, #'{'dataList[{0}].name})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO P_SPECIFICATION_GROUP (id, name) VALUES "
        + String.join(", ", rows);
    }
    // 选择性更新SQL
    public String dynamicUpdate(PSpecificationGroupPo pSpecificationGroup) {
        return new SQL() {
            {
                UPDATE("P_SPECIFICATION_GROUP");
                if (Objects.nonNull(pSpecificationGroup.getName())) SET("name=#{name,jdbcType=VARCHAR}");
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
                FROM("P_SPECIFICATION_GROUP");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name like CONCAT('%',#{param.name},'%')");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name = #{param.name}");
            }
        }.toString();
    }


}