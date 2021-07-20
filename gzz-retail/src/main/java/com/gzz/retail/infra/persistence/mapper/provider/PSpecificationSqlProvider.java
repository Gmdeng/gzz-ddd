package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.infra.persistence.pojo.PSpecificationPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2021-07-20 16:37:04
 */
public class PSpecificationSqlProvider {

    // 选择性新增SQL insertSelective
    public String dynamicInsert(PSpecificationPo pSpecification) {
        return new SQL(){
            {
                INSERT_INTO("P_SPECIFICATION");
                if (Objects.nonNull(pSpecification.getId())) VALUES("id", "#{id, jdbcType=BIGINT}");
                if (Objects.nonNull(pSpecification.getParamLabel())) VALUES("param_label", "#{paramLabel, jdbcType=VARCHAR}");
                if (Objects.nonNull(pSpecification.getParamValue())) VALUES("param_value", "#{paramValue, jdbcType=VARCHAR}");
                if (Objects.nonNull(pSpecification.getGroupId())) VALUES("group_id", "#{groupId, jdbcType=BIGINT}");
            }
        }.toString();
    }

    // 批量插入
    public String batchInsert(@Param("dataList") List<PSpecificationPo> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].id}, #'{'dataList[{0}].paramLabel}, #'{'dataList[{0}].paramValue}, #'{'dataList[{0}].groupId})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO P_SPECIFICATION (id, param_label, param_value, group_id) VALUES "
        + String.join(", ", rows);
    }
    // 选择性更新SQL
    public String dynamicUpdate(PSpecificationPo pSpecification) {
        return new SQL() {
            {
                UPDATE("P_SPECIFICATION");
                if (Objects.nonNull(pSpecification.getParamLabel())) SET("param_label=#{paramLabel,jdbcType=VARCHAR}");
                if (Objects.nonNull(pSpecification.getParamValue())) SET("param_value=#{paramValue,jdbcType=VARCHAR}");
                if (Objects.nonNull(pSpecification.getGroupId())) SET("group_id=#{groupId,jdbcType=BIGINT}");
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
                FROM("P_SPECIFICATION");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name like CONCAT('%',#{param.name},'%')");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name = #{param.name}");
            }
        }.toString();
    }


}