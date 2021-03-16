package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.PGoodsAttrSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PGoodsAttr;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@CacheNamespace //开启缓存
public interface IPGoodsAttrMapper {
    /*================================查找===========================================*/
    // 根据主键id
    @Select({"select * form p_goods_attr  where id = #{id}"})
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "goodsId", column = "goods_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "values", column = "values"),
    })
    PGoodsAttr getById(Long id);

    // 列表
    @SelectProvider(type = PGoodsAttrSqlProvider.class, method = "findList")
    List<PGoodsAttr> findLists(@Param("param") ParamMap param);

    <T> List<T> findList(@Param("param") ParamMap param);

    // 分页列表
    @SelectProvider(type = PGoodsAttrSqlProvider.class, method = "findListByPage")
    List<PGoodsAttr> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    <T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    /*================================查找===========================================*/
    // 增加
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @Insert({"insert into p_goods_attr(id, goods_id, name, values) ",
            "values(",
            "#{id}, #{goodsId}, #{name}, #{values}",
            ")"})
    int insert(PGoodsAttr pGoodsAttr);

    @InsertProvider(type = PGoodsAttrSqlProvider.class, method = "insertSelective")
    int insertSelective(PGoodsAttr pGoodsAttr);

    /* 批量增加 */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @InsertProvider(type = PGoodsAttrSqlProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<PGoodsAttr> pGoodsAttrList);

    // 删除
    @Delete("delete from p_goods_attr where id = #{id}")
    int delete(Long id);

    // 修改
    //@Update({"update p_goods_attr set ",
    //		"goods_id=#{goodsId}, name=#{name}, values=#{values}"
    //		," where id = #{id}"})
    @UpdateProvider(type = PGoodsAttrSqlProvider.class, method = "update")
    int update(PGoodsAttr pGoodsAttr);
}