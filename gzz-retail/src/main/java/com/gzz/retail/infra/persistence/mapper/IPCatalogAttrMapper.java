package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.PCatalogAttrSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PCatalogAttrPo;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@CacheNamespace //开启缓存
public interface IPCatalogAttrMapper {
    /*================================查找===========================================*/
    // 根据主键id
    @Select({"select * form p_catalog_attr  where id = #{id}"})
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "catalogId", column = "catalog_id"),
            @Result(property = "name", column = "name"),
            @Result(property = "values", column = "values"),
    })
    PCatalogAttrPo getById(Long id);

    // 列表
    @SelectProvider(type = PCatalogAttrSqlProvider.class, method = "findList")
    List<PCatalogAttrPo> findLists(@Param("param") ParamMap param);

    <T> List<T> findList(@Param("param") ParamMap param);

    // 分页列表
    @SelectProvider(type = PCatalogAttrSqlProvider.class, method = "findListByPage")
    List<PCatalogAttrPo> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    <T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    /*================================查找===========================================*/
    // 增加
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @Insert({"insert into p_catalog_attr(id, catalog_id, name, values) ",
            "values(",
            "#{id}, #{catalogId}, #{name}, #{values}",
            ")"})
    int insert(PCatalogAttrPo pCatalogAttr);

    @InsertProvider(type = PCatalogAttrSqlProvider.class, method = "insertSelective")
    int insertSelective(PCatalogAttrPo pCatalogAttr);

    /* 批量增加 */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @InsertProvider(type = PCatalogAttrSqlProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<PCatalogAttrPo> pCatalogAttrList);

    // 删除
    @Delete("delete from p_catalog_attr where id = #{id}")
    int delete(Long id);

    // 修改
    //@Update({"update p_catalog_attr set ",
    //		"catalog_id=#{catalogId}, name=#{name}, values=#{values}"
    //		," where id = #{id}"})
    @UpdateProvider(type = PCatalogAttrSqlProvider.class, method = "update")
    int update(PCatalogAttrPo pCatalogAttr);
}