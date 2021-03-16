package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.infra.persistence.pojo.PGoods;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2021-01-14 23:37:32
 */
public class PGoodsSqlProvider {

    // 选择性新增SQL insertSelective
    public String dynamicInsert(PGoods pGoods) {
        return new SQL() {
            {
                INSERT_INTO("P_GOODS");
                if (!StringUtil.isNull(pGoods.getId())) {
                    VALUES("id", "#{id, jdbcType=BIGINT}");
                }
                if (!StringUtil.isNull(pGoods.getCatalogId())) {
                    VALUES("catalog_id", "#{catalogId, jdbcType=BIGINT}");
                }
                if (!StringUtil.isNull(pGoods.getCode())) {
                    VALUES("code", "#{code, jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getName())) {
                    VALUES("name", "#{name, jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getBarCode())) {
                    VALUES("bar_code", "#{barCode, jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getBrandId())) {
                    VALUES("brand_id", "#{brandId, jdbcType=BIGINT}");
                }
                if (!StringUtil.isNull(pGoods.getThumb())) {
                    VALUES("thumb", "#{thumb, jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getUnit())) {
                    VALUES("unit", "#{unit, jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getSpec())) {
                    VALUES("spec", "#{spec, jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getIdx())) {
                    VALUES("idx", "#{idx, jdbcType=TINYINT}");
                }
                if (!StringUtil.isNull(pGoods.getSalePrice())) {
                    VALUES("sale_price", "#{salePrice, jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getMarketPrice())) {
                    VALUES("market_price", "#{marketPrice, jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getPv())) {
                    VALUES("pv", "#{pv, jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getStatus())) {
                    VALUES("status", "#{status, jdbcType=TINYINT}");
                }
                if (!StringUtil.isNull(pGoods.getAverageCost())) {
                    VALUES("average_cost", "#{averageCost, jdbcType=DECIMAL}");
                }
            }
        }.toString();
    }

    // 批量插入
    public String batchInsert(@Param("dataList") List<PGoods> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].id}, #'{'dataList[{0}].catalogId}, #'{'dataList[{0}].code}, #'{'dataList[{0}].name}, #'{'dataList[{0}].barCode}, #'{'dataList[{0}].brandId}, #'{'dataList[{0}].thumb}, #'{'dataList[{0}].unit}, #'{'dataList[{0}].spec}, #'{'dataList[{0}].idx}, #'{'dataList[{0}].salePrice}, #'{'dataList[{0}].marketPrice}, #'{'dataList[{0}].pv}, #'{'dataList[{0}].status}, #'{'dataList[{0}].averageCost})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[]{i}));
        }
        return "INSERT INTO P_GOODS (id, catalog_id, code, name, bar_code, brand_id, thumb, unit, spec, idx, sale_price, market_price, pv, status, average_cost) VALUES "
                + String.join(", ", rows);
    }

    // 选择性更新SQL
    public String dynamicUpdate(PGoods pGoods) {
        return new SQL() {
            {
                UPDATE("P_GOODS");
                if (!StringUtil.isNull(pGoods.getCatalogId())) {
                    SET("catalog_id=#{catalogId,jdbcType=BIGINT}");
                }
                if (!StringUtil.isNull(pGoods.getCode())) {
                    SET("code=#{code,jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getName())) {
                    SET("name=#{name,jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getBarCode())) {
                    SET("bar_code=#{barCode,jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getBrandId())) {
                    SET("brand_id=#{brandId,jdbcType=BIGINT}");
                }
                if (!StringUtil.isNull(pGoods.getThumb())) {
                    SET("thumb=#{thumb,jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getUnit())) {
                    SET("unit=#{unit,jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getSpec())) {
                    SET("spec=#{spec,jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getIdx())) {
                    SET("idx=#{idx,jdbcType=TINYINT}");
                }
                if (!StringUtil.isNull(pGoods.getSalePrice())) {
                    SET("sale_price=#{salePrice,jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getMarketPrice())) {
                    SET("market_price=#{marketPrice,jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getPv())) {
                    SET("pv=#{pv,jdbcType=VARCHAR}");
                }
                if (!StringUtil.isNull(pGoods.getStatus())) {
                    SET("status=#{status,jdbcType=TINYINT}");
                }
                if (!StringUtil.isNull(pGoods.getAverageCost())) {
                    SET("average_cost=#{averageCost,jdbcType=DECIMAL}");
                }
                WHERE("id=#{id}");
            }
        }.toString();
    }

    // 列表分页查询
    public String findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager) {
        return findList(param);
    }

    // 列表查询
    public String findList(@Param("param") ParamMap param) {
        return new SQL() {
            {
                SELECT("*");
                FROM("P_GOODS");
                if (StringUtil.isNotEmpty(param.get("name"))) WHERE("name like CONCAT('%',#{param.name},'%')");
                if (StringUtil.isNotEmpty(param.get("name"))) WHERE("name = #{param.name}");
            }
        }.toString();
    }


}