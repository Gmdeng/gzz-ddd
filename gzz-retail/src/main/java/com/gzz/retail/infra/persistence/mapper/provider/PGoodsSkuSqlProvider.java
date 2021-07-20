package com.gzz.retail.infra.persistence.mapper.provider;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.infra.persistence.pojo.PGoodsSkuPo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * @Description: 主要用途：根据复杂的业务需求来动态生成SQL.
 * @Date 2021-07-20 16:36:29
 */
public class PGoodsSkuSqlProvider {

    // 选择性新增SQL insertSelective
    public String dynamicInsert(PGoodsSkuPo pGoodsSku) {
        return new SQL(){
            {
                INSERT_INTO("P_GOODS_SKU");
                if (Objects.nonNull(pGoodsSku.getId())) VALUES("id", "#{id, jdbcType=BIGINT}");
                if (Objects.nonNull(pGoodsSku.getGoodsId())) VALUES("goods_id", "#{goodsId, jdbcType=BIGINT}");
                if (Objects.nonNull(pGoodsSku.getAttrsOwn())) VALUES("attrs_own", "#{attrsOwn, jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSku.getAttrsIndexes())) VALUES("attrs_indexes", "#{attrsIndexes, jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSku.getPrice())) VALUES("price", "#{price, jdbcType=DECIMAL}");
                if (Objects.nonNull(pGoodsSku.getPv())) VALUES("pv", "#{pv, jdbcType=DECIMAL}");
                if (Objects.nonNull(pGoodsSku.getStatus())) VALUES("status", "#{status, jdbcType=TINYINT}");
                if (Objects.nonNull(pGoodsSku.getThumb())) VALUES("thumb", "#{thumb, jdbcType=VARCHAR}");
            }
        }.toString();
    }

    // 批量插入
    public String batchInsert(@Param("dataList") List<PGoodsSkuPo> dataList) {
        MessageFormat mf = new MessageFormat("(#'{'dataList[{0}].id}, #'{'dataList[{0}].goodsId}, #'{'dataList[{0}].attrsOwn}, #'{'dataList[{0}].attrsIndexes}, #'{'dataList[{0}].price}, #'{'dataList[{0}].pv}, #'{'dataList[{0}].status}, #'{'dataList[{0}].thumb})");
        List<String> rows = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            rows.add(mf.format(new Object[] { i }));
        }
        return "INSERT INTO P_GOODS_SKU (id, goods_id, attrs_own, attrs_indexes, price, pv, status, thumb) VALUES "
        + String.join(", ", rows);
    }
    // 选择性更新SQL
    public String dynamicUpdate(PGoodsSkuPo pGoodsSku) {
        return new SQL() {
            {
                UPDATE("P_GOODS_SKU");
                if (Objects.nonNull(pGoodsSku.getGoodsId())) SET("goods_id=#{goodsId,jdbcType=BIGINT}");
                if (Objects.nonNull(pGoodsSku.getAttrsOwn())) SET("attrs_own=#{attrsOwn,jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSku.getAttrsIndexes())) SET("attrs_indexes=#{attrsIndexes,jdbcType=VARCHAR}");
                if (Objects.nonNull(pGoodsSku.getPrice())) SET("price=#{price,jdbcType=DECIMAL}");
                if (Objects.nonNull(pGoodsSku.getPv())) SET("pv=#{pv,jdbcType=DECIMAL}");
                if (Objects.nonNull(pGoodsSku.getStatus())) SET("status=#{status,jdbcType=TINYINT}");
                if (Objects.nonNull(pGoodsSku.getThumb())) SET("thumb=#{thumb,jdbcType=VARCHAR}");
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
                FROM("P_GOODS_SKU");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name like CONCAT('%',#{param.name},'%')");
                if(StringUtil.isNotEmpty(param.get("name"))) WHERE("name = #{param.name}");
            }
        }.toString();
    }


}