package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.pojo.PCatalogAttr;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2020-10-10 11:01:57
 */
public class PCatalogAttrSqlProvider {

    // 选择性新增SQL
    public String insertSelective(PCatalogAttr pCatalogAttr) {
        return new SQL() {
            {
                INSERT_INTO("p_catalog_attr");
                if (pCatalogAttr.getId() != null) {
                    VALUES("id", "#{id}");
                }
                if (pCatalogAttr.getCatalogId() != null) {
                    VALUES("catalog_id", "#{catalogId}");
                }
                if (pCatalogAttr.getName() != null) {
                    VALUES("name", "#{name}");
                }
                if (pCatalogAttr.getValues() != null) {
                    VALUES("values", "#{values}");
                }
            }
        }.toString();
    }

    // 批量插入
    public String insertBatch(Map<String, List<PCatalogAttr>> map) {
        List<PCatalogAttr> list = (List<PCatalogAttr>) map.get("list");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id}, #'{'list[{0}].catalogId}, #'{'list[{0}].name}, #'{'list[{0}].values})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            rows.add(mf.format(new Object[]{i}));
        }
        return "INSERT INTO p_catalog_attr (id, catalog_id, name, values) VALUES "
                + String.join(", ", rows);

    }

    // 选择性更新SQL
    public String update(PCatalogAttr pCatalogAttr) {
        return new SQL() {
            {
                UPDATE("p_catalog_attr");
                if (pCatalogAttr.getCatalogId() != null) {
                    SET("catalog_id=#{catalogId}");
                }
                if (pCatalogAttr.getName() != null) {
                    SET("name=#{name}");
                }
                if (pCatalogAttr.getValues() != null) {
                    SET("values=#{values}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

    // 列表分页查询
    public String findListByPage(ParamMap param, Pager pager) {
        return new SQL() {
            {
                SELECT("*");
                FROM("p_catalog_attr");
                if (param.get("name") != null) WHERE("name like CONCAT('%',#{param.name},'%')");
            }
        }.toString();
    }

    // 列表查询
    public String findList(ParamMap param) {
        return new SQL() {
            {
                SELECT("*");
                FROM("p_catalog_attr");
                if (param.get("name") != null) WHERE("name like CONCAT('%',#{param.name},'%')");

            }
        }.toString();
    }


}