package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.pojo.PSku;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2020-10-10 11:01:14
 */
public class PSkuSqlProvider {

    // 选择性新增SQL
    public String insertSelective(PSku pSku) {
        return new SQL() {
            {
                INSERT_INTO("p_sku");
                if (pSku.getId() != null) {
                    VALUES("id", "#{id}");
                }
                if (pSku.getGoodsId() != null) {
                    VALUES("goods_id", "#{goodsId}");
                }
                if (pSku.getAttrSet() != null) {
                    VALUES("attr_set", "#{attrSet}");
                }
                if (pSku.getSalePrice() != null) {
                    VALUES("sale_price", "#{salePrice}");
                }
                if (pSku.getStatus() != null) {
                    VALUES("status", "#{status}");
                }
                if (pSku.getThumb() != null) {
                    VALUES("thumb", "#{thumb}");
                }
                if (pSku.getPv() != null) {
                    VALUES("pv", "#{pv}");
                }
            }
        }.toString();
    }

    // 批量插入
    public String insertBatch(Map<String, List<PSku>> map) {
        List<PSku> list = (List<PSku>) map.get("list");
        MessageFormat mf = new MessageFormat("(#'{'list[{0}].id}, #'{'list[{0}].goodsId}, #'{'list[{0}].attrSet}, #'{'list[{0}].salePrice}, #'{'list[{0}].status}, #'{'list[{0}].thumb}, #'{'list[{0}].pv})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            rows.add(mf.format(new Object[]{i}));
        }
        return "INSERT INTO p_sku (id, goods_id, attr_set, sale_price, status, thumb, pv) VALUES "
                + String.join(", ", rows);

    }

    // 选择性更新SQL
    public String update(PSku pSku) {
        return new SQL() {
            {
                UPDATE("p_sku");
                if (pSku.getGoodsId() != null) {
                    SET("goods_id=#{goodsId}");
                }
                if (pSku.getAttrSet() != null) {
                    SET("attr_set=#{attrSet}");
                }
                if (pSku.getSalePrice() != null) {
                    SET("sale_price=#{salePrice}");
                }
                if (pSku.getStatus() != null) {
                    SET("status=#{status}");
                }
                if (pSku.getThumb() != null) {
                    SET("thumb=#{thumb}");
                }
                if (pSku.getPv() != null) {
                    SET("pv=#{pv}");
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
                FROM("p_sku");
                if (param.get("name") != null) WHERE("name like CONCAT('%',#{param.name},'%')");
            }
        }.toString();
    }

    // 列表查询
    public String findList(ParamMap param) {
        return new SQL() {
            {
                SELECT("*");
                FROM("p_sku");
                if (param.get("name") != null) WHERE("name like CONCAT('%',#{param.name},'%')");

            }
        }.toString();
    }


}