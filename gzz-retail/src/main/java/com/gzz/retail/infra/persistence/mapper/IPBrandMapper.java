package com.gzz.retail.infra.persistence.mapper;


import com.gzz.retail.infra.persistence.mapper.provider.PBrandSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PBrand;
import org.apache.ibatis.annotations.*;

import java.util.List;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;

@Mapper
@CacheNamespace //开启缓存
public interface IPBrandMapper {
	/*================================查找===========================================*/
	// 根据主键id
	@Select({"select * form p_brand  where id = #{id}"})
	@Results(value = {
	@Result(property = "id", column = "id"),
		@Result(property = "cnName", column = "cn_name"),
		@Result(property = "enName", column = "en_name"),
		@Result(property = "logo", column = "logo"),
		@Result(property = "website", column = "website"),
		@Result(property = "stroy", column = "stroy"),
	})
	PBrand getById(Long id);

	// 列表
	@SelectProvider(type = PBrandSqlProvider.class, method = "findList")
	List<PBrand> findLists(@Param("param") ParamMap param);
	<T> List<T> findList(@Param("param") ParamMap param);
	// 分页列表
	@SelectProvider(type = PBrandSqlProvider.class, method = "findListByPage")
	List<PBrand> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);
	<T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

	/*================================查找===========================================*/
	// 增加
	@Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
	@Insert({"insert into p_brand(id, cn_name, en_name, logo, website, stroy) ",
		"values(",
		"#{id}, #{cnName}, #{enName}, #{logo}, #{website}, #{stroy}",
        ")" })
	int insert(PBrand pBrand);

	@InsertProvider(type = PBrandSqlProvider.class, method = "insertSelective")
    int insertSelective(PBrand pBrand);

	/* 批量增加 */
	@Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
	@InsertProvider(type = PBrandSqlProvider.class, method = "insertBatch")
	int insertBatch(@Param("list") List<PBrand> pBrandList);

	// 删除
	@Delete("delete from p_brand where id = #{id}")
	int delete(Long id);

	// 修改
	//@Update({"update p_brand set ",
	//		"cn_name=#{cnName}, en_name=#{enName}, logo=#{logo}, website=#{website}, stroy=#{stroy}"
	//		," where id = #{id}"})
	@UpdateProvider(type = PBrandSqlProvider.class, method = "update")
	int update(PBrand pBrand);
}