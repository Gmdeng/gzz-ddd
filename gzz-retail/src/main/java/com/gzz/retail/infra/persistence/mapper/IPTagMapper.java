package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.PTagSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PGoodsTag;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@CacheNamespace //开启缓存
public interface IPTagMapper {
    /*================================查找===========================================*/
    // 根据主键id
    @Select({"select * form p_tag  where id = #{id}"})
    @Results(value = {
            @Result(property = "id", column = "id"),
            @Result(property = "cnName", column = "cn_name"),
            @Result(property = "enName", column = "en_name"),
            @Result(property = "logo", column = "logo"),
            @Result(property = "website", column = "website"),
            @Result(property = "stroy", column = "stroy"),
    })
    PGoodsTag getById(Long id);

    // 列表
    @SelectProvider(type = PTagSqlProvider.class, method = "findList")
    List<PGoodsTag> findLists(@Param("param") ParamMap param);

    <T> List<T> findList(@Param("param") ParamMap param);

    // 分页列表
    @SelectProvider(type = PTagSqlProvider.class, method = "findListByPage")
    List<PGoodsTag> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    <T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    /*================================查找===========================================*/
    // 增加
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @Insert({"insert into p_tag(id, cn_name, en_name, logo, website, stroy) ",
            "values(",
            "#{id}, #{cnName}, #{enName}, #{logo}, #{website}, #{stroy}",
            ")"})
    int insert(PGoodsTag pTag);

    @InsertProvider(type = PTagSqlProvider.class, method = "insertSelective")
    int insertSelective(PGoodsTag pTag);

    /* 批量增加 */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @InsertProvider(type = PTagSqlProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<PGoodsTag> pTagList);

    // 删除
    @Delete("delete from p_tag where id = #{id}")
    int delete(Long id);

    // 修改
    //@Update({"update p_tag set ",
    //		"cn_name=#{cnName}, en_name=#{enName}, logo=#{logo}, website=#{website}, stroy=#{stroy}"
    //		," where id = #{id}"})
    @UpdateProvider(type = PTagSqlProvider.class, method = "update")
    int update(PGoodsTag pTag);
}