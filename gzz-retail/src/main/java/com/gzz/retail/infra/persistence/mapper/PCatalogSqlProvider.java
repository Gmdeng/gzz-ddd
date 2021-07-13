package com.gzz.retail.infra.persistence.mapper;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.Objects;

import com.gzz.retail.infra.persistence.pojo.PCatalogPo;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.annotations.Param;
import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;


/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2021-07-13 11:54:13
 */
public class PCatalogSqlProvider {

    // 选择性新增SQL insertSelective
    public String dynamicInsert(PCatalogPo pCatalog) {
        return new SQL(){
            {
                INSERT_INTO("P_CATALOG");
                if (Objects.nonNull(pCatalog.getId())) VALUES("id", "#{id, jdbcType=BIGINT}");
                if (Objects.nonNull(pCatalog.getParentId())) VALUES("parent_id", "#{parentId, jdbcType=BIGINT}");
                if (Objects.nonNull(pCatalog.getName())) VALUES("name", "#{name, jdbcType=VARCHAR}");
                if (Objects.nonNull(pCatalog.getThumb())) VALUES("thumb", "#{thumb, jdbcType=VARCHAR}");
                if (Objects.nonNull(pCatalog.getIdx())) VALUES("idx", "#{idx, jdbcType=TINYINT}");
                if (Objects.nonNull(pCatalog.getKeywords())) VALUES("keywords", "#{keywords, jdbcType=VARCHAR}");
                if (Objects.nonNull(pCatalog.getNotes())) VALUES("notes", "#{notes, jdbcType=VARCHAR}");
                if (Objects.nonNull(pCatalog.isEnable())) VALUES("enable", "#{enable, jdbcType=TINYINT}");
            }
        }.toString();
    }

    // 批量插入
    public String batchInsert(@Param("dataList") List<PCatalogPo> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].id}, #'{'dataList[{0}].parentId}, #'{'dataList[{0}].name}, #'{'dataList[{0}].thumb}, #'{'dataList[{0}].idx}, #'{'dataList[{0}].keywords}, #'{'dataList[{0}].notes}, #'{'dataList[{0}].enable})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO P_CATALOG (id, parent_id, name, thumb, idx, keywords, notes, enable) VALUES "
        + String.join(", ", rows);
    }
    // 选择性更新SQL
    public String dynamicUpdate(PCatalogPo pCatalog) {
        return new SQL() {
            {
                UPDATE("P_CATALOG");
                if (Objects.nonNull(pCatalog.getParentId())) SET("parent_id=#{parentId,jdbcType=BIGINT}");
                if (Objects.nonNull(pCatalog.getName())) SET("name=#{name,jdbcType=VARCHAR}");
                if (Objects.nonNull(pCatalog.getThumb())) SET("thumb=#{thumb,jdbcType=VARCHAR}");
                if (Objects.nonNull(pCatalog.getIdx())) SET("idx=#{idx,jdbcType=TINYINT}");
                if (Objects.nonNull(pCatalog.getKeywords())) SET("keywords=#{keywords,jdbcType=VARCHAR}");
                if (Objects.nonNull(pCatalog.getNotes())) SET("notes=#{notes,jdbcType=VARCHAR}");
                if (Objects.nonNull(pCatalog.isEnable())) SET("enable=#{enable,jdbcType=TINYINT}");
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
                FROM("P_CATALOG");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name like CONCAT('%',#{param.name},'%')");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name = #{param.name}");
            }
        }.toString();
    }


}