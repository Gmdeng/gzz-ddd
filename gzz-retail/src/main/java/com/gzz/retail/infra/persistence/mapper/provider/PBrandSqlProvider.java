package com.gzz.retail.infra.persistence.mapper.provider;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Objects;

import com.gzz.retail.infra.persistence.pojo.PBrandPo;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.annotations.Param;
import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;


/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2021-07-15 16:05:57
 */
public class PBrandSqlProvider {

    // 选择性新增SQL insertSelective
    public String dynamicInsert(PBrandPo pBrand) {
        return new SQL(){
            {
                INSERT_INTO("P_BRAND");
                if (Objects.nonNull(pBrand.getId())) VALUES("id", "#{id, jdbcType=BIGINT}");
                if (Objects.nonNull(pBrand.getCnName())) VALUES("cn_name", "#{cnName, jdbcType=VARCHAR}");
                if (Objects.nonNull(pBrand.getEnName())) VALUES("en_name", "#{enName, jdbcType=VARCHAR}");
                if (Objects.nonNull(pBrand.getLogo())) VALUES("logo", "#{logo, jdbcType=VARCHAR}");
                if (Objects.nonNull(pBrand.getWebsite())) VALUES("website", "#{website, jdbcType=VARCHAR}");
                if (Objects.nonNull(pBrand.getStroy())) VALUES("stroy", "#{stroy, jdbcType=VARCHAR}");
                if (Objects.nonNull(pBrand.getSummary())) VALUES("summary", "#{summary, jdbcType=VARCHAR}");
            }
        }.toString();
    }

    // 批量插入
    public String batchInsert(@Param("dataList") List<PBrandPo> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].id}, #'{'dataList[{0}].cnName}, #'{'dataList[{0}].enName}, #'{'dataList[{0}].logo}, #'{'dataList[{0}].website}, #'{'dataList[{0}].stroy}, #'{'dataList[{0}].summary})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO P_BRAND (id, cn_name, en_name, logo, website, stroy, summary) VALUES "
                + String.join(", ", rows);
    }
    // 选择性更新SQL
    public String dynamicUpdate(PBrandPo pBrand) {
        return new SQL() {
            {
                UPDATE("P_BRAND");
                if (Objects.nonNull(pBrand.getCnName())) SET("cn_name=#{cnName,jdbcType=VARCHAR}");
                if (Objects.nonNull(pBrand.getEnName())) SET("en_name=#{enName,jdbcType=VARCHAR}");
                if (Objects.nonNull(pBrand.getLogo())) SET("logo=#{logo,jdbcType=VARCHAR}");
                if (Objects.nonNull(pBrand.getWebsite())) SET("website=#{website,jdbcType=VARCHAR}");
                if (Objects.nonNull(pBrand.getStroy())) SET("stroy=#{stroy,jdbcType=VARCHAR}");
                if (Objects.nonNull(pBrand.getSummary())) SET("summary=#{summary,jdbcType=VARCHAR}");
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
                FROM("P_BRAND");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name like CONCAT('%',#{param.name},'%')");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name = #{param.name}");
            }
        }.toString();
    }


}