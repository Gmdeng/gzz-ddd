package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.PSkuSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PSku;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@CacheNamespace //开启缓存
public interface IPSkuMapper {
    /*================================查找===========================================*/
    // 根据主键id
    @Select({"select * form p_sku  where id = #{id}"})
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "goodsId", column = "goods_id"),
            @Result(property = "attrSet", column = "attr_set"),
            @Result(property = "salePrice", column = "sale_price"),
            @Result(property = "status", column = "status"),
            @Result(property = "thumb", column = "thumb"),
            @Result(property = "pv", column = "pv"),
    })
    PSku getById(Long id);

    // 列表
    @SelectProvider(type = PSkuSqlProvider.class, method = "findList")
    List<PSku> findLists(@Param("param") ParamMap param);

    <T> List<T> findList(@Param("param") ParamMap param);

    // 分页列表
    @SelectProvider(type = PSkuSqlProvider.class, method = "findListByPage")
    List<PSku> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    <T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    /*================================查找===========================================*/
    // 增加
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @Insert({"insert into p_sku(id, goods_id, attr_set, sale_price, status, thumb, pv) ",
            "values(",
            "#{id}, #{goodsId}, #{attrSet}, #{salePrice}, #{status}, #{thumb}, #{pv}",
            ")"})
    int insert(PSku pSku);

    @InsertProvider(type = PSkuSqlProvider.class, method = "insertSelective")
    int insertSelective(PSku pSku);

    /* 批量增加 */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @InsertProvider(type = PSkuSqlProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<PSku> pSkuList);

    // 删除
    @Delete("delete from p_sku where id = #{id}")
    int delete(Long id);

    // 修改
    //@Update({"update p_sku set ",
    //		"goods_id=#{goodsId}, attr_set=#{attrSet}, sale_price=#{salePrice}, status=#{status}, thumb=#{thumb}, pv=#{pv}"
    //		," where id = #{id}"})
    @UpdateProvider(type = PSkuSqlProvider.class, method = "update")
    int update(PSku pSku);
}