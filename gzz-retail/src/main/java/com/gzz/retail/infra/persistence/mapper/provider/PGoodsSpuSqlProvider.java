package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.infra.persistence.pojo.PGoodsSpuPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2021-07-20 16:36:44
 */
public class PGoodsSpuSqlProvider {

    // 选择性新增SQL insertSelective
    public String dynamicInsert(PGoodsSpuPo pGoodsSpu) {
        return new SQL(){
            {
                INSERT_INTO("P_GOODS_SPU");
                if (Objects.nonNull(pGoodsSpu.getId())) VALUES("id", "#{id, jdbcType=BIGINT}");
                if (Objects.nonNull(pGoodsSpu.getCatalogId())) VALUES("catalog_id", "#{catalogId, jdbcType=BIGINT}");
                if (Objects.nonNull(pGoodsSpu.getName())) VALUES("name", "#{name, jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSpu.getCode())) VALUES("code", "#{code, jdbcType=CHAR}");
                if (Objects.nonNull(pGoodsSpu.getBarCode())) VALUES("bar_code", "#{barCode, jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSpu.getBrandId())) VALUES("brand_id", "#{brandId, jdbcType=BIGINT}");
                if (Objects.nonNull(pGoodsSpu.getThumb())) VALUES("thumb", "#{thumb, jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSpu.getUnit())) VALUES("unit", "#{unit, jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSpu.getSpecsOwn())) VALUES("specs_own", "#{specsOwn, jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSpu.getIdx())) VALUES("idx", "#{idx, jdbcType=TINYINT}");
                if (Objects.nonNull(pGoodsSpu.getPrice())) VALUES("price", "#{price, jdbcType=DECIMAL}");
                if (Objects.nonNull(pGoodsSpu.getPv())) VALUES("pv", "#{pv, jdbcType=DECIMAL}");
                if (Objects.nonNull(pGoodsSpu.getStatus())) VALUES("status", "#{status, jdbcType=TINYINT}");
                if (Objects.nonNull(pGoodsSpu.getAverageCost())) VALUES("average_cost", "#{averageCost, jdbcType=DECIMAL}");
                if (Objects.nonNull(pGoodsSpu.getFreight())) VALUES("freight", "#{freight, jdbcType=DECIMAL}");
            }
        }.toString();
    }

    // 批量插入
    public String batchInsert(@Param("dataList") List<PGoodsSpuPo> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].id}, #'{'dataList[{0}].catalogId}, #'{'dataList[{0}].name}, #'{'dataList[{0}].code}, #'{'dataList[{0}].barCode}, #'{'dataList[{0}].brandId}, #'{'dataList[{0}].thumb}, #'{'dataList[{0}].unit}, #'{'dataList[{0}].specsOwn}, #'{'dataList[{0}].idx}, #'{'dataList[{0}].price}, #'{'dataList[{0}].pv}, #'{'dataList[{0}].status}, #'{'dataList[{0}].averageCost}, #'{'dataList[{0}].freight})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO P_GOODS_SPU (id, catalog_id, name, code, bar_code, brand_id, thumb, unit, specs_own, idx, price, pv, status, average_cost, freight) VALUES "
        + String.join(", ", rows);
    }
    // 选择性更新SQL
    public String dynamicUpdate(PGoodsSpuPo pGoodsSpu) {
        return new SQL() {
            {
                UPDATE("P_GOODS_SPU");
                if (Objects.nonNull(pGoodsSpu.getCatalogId())) SET("catalog_id=#{catalogId,jdbcType=BIGINT}");
                if (Objects.nonNull(pGoodsSpu.getName())) SET("name=#{name,jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSpu.getCode())) SET("code=#{code,jdbcType=CHAR}");
                if (Objects.nonNull(pGoodsSpu.getBarCode())) SET("bar_code=#{barCode,jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSpu.getBrandId())) SET("brand_id=#{brandId,jdbcType=BIGINT}");
                if (Objects.nonNull(pGoodsSpu.getThumb())) SET("thumb=#{thumb,jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSpu.getUnit())) SET("unit=#{unit,jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSpu.getSpecsOwn())) SET("specs_own=#{specsOwn,jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSpu.getIdx())) SET("idx=#{idx,jdbcType=TINYINT}");
                if (Objects.nonNull(pGoodsSpu.getPrice())) SET("price=#{price,jdbcType=DECIMAL}");
                if (Objects.nonNull(pGoodsSpu.getPv())) SET("pv=#{pv,jdbcType=DECIMAL}");
                if (Objects.nonNull(pGoodsSpu.getStatus())) SET("status=#{status,jdbcType=TINYINT}");
                if (Objects.nonNull(pGoodsSpu.getAverageCost())) SET("average_cost=#{averageCost,jdbcType=DECIMAL}");
                if (Objects.nonNull(pGoodsSpu.getFreight())) SET("freight=#{freight,jdbcType=DECIMAL}");
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
                FROM("P_GOODS_SPU");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name like CONCAT('%',#{param.name},'%')");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name = #{param.name}");
            }
        }.toString();
    }


}