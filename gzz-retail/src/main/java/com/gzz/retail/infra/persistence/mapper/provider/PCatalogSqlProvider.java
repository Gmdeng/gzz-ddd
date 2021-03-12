package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.pojo.PCatalog;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2020-10-10 11:02:06
 */
public class PCatalogSqlProvider {

    // 选择性新增SQL
    public String insertSelective(PCatalog pCatalog) {
        return new SQL(){
            {
                INSERT_INTO("p_catalog");
                if (pCatalog.getId() != null) {VALUES("id", "#{id}");}
                if (pCatalog.getParentId() != null) {VALUES("parent_id", "#{parentId}");}
                if (pCatalog.getName() != null) {VALUES("name", "#{name}");}
                if (pCatalog.getThumb() != null) {VALUES("thumb", "#{thumb}");}
                if (pCatalog.getIdx() != null) {VALUES("idx", "#{idx}");}
                if (pCatalog.getKeywords() != null) {VALUES("keywords", "#{keywords}");}
                if (pCatalog.getNotes() != null) {VALUES("notes", "#{notes}");}
            }
        }.toString();
    }

    // 批量插入
    public String insertBatch(Map<String, List<PCatalog>> map) {
        List<PCatalog> list = (List<PCatalog>) map.get("list");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id}, #'{'list[{0}].parentId}, #'{'list[{0}].name}, #'{'list[{0}].thumb}, #'{'list[{0}].idx}, #'{'list[{0}].keywords}, #'{'list[{0}].notes})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO p_catalog (id, parent_id, name, thumb, idx, keywords, notes) VALUES "
        + String.join(", ", rows);

    }
    // 选择性更新SQL
    public String update(PCatalog pCatalog) {
        return new SQL() {
            {
                UPDATE("p_catalog");
                if (pCatalog.getParentId() != null) {SET("parent_id=#{parentId}");}
                if (pCatalog.getName() != null) {SET("name=#{name}");}
                if (pCatalog.getThumb() != null) {SET("thumb=#{thumb}");}
                if (pCatalog.getIdx() != null) {SET("idx=#{idx}");}
                if (pCatalog.getKeywords() != null) {SET("keywords=#{keywords}");}
                if (pCatalog.getNotes() != null) {SET("notes=#{notes}");}
                WHERE("id=#{id}");
            }
        }.toString();
    }

    // 列表分页查询
    public String findListByPage(ParamMap param, Pager pager) {
        return new SQL() {
            {
                SELECT("*");
                FROM("p_catalog");
                if(param.get("name") !=null ) WHERE("name like CONCAT('%',#{param.name},'%')");
            }
        }.toString();
    }

    // 列表查询
    public String findList(ParamMap param) {
        return new SQL() {
            {
                SELECT("*");
                FROM("p_catalog");
                if(param.get("name") !=null ) WHERE("name like CONCAT('%',#{param.name},'%')");

            }
        }.toString();
    }


}