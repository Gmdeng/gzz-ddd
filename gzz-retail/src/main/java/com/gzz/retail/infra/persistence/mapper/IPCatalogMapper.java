package com.gzz.retail.infra.persistence.mapper;


import com.gzz.retail.infra.persistence.mapper.provider.PCatalogSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PCatalog;
import org.apache.ibatis.annotations.*;

import java.util.List;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;

@Mapper
@CacheNamespace //开启缓存
public interface IPCatalogMapper {
	/*================================查找===========================================*/
	// 根据主键id
	@Select({"select * form p_catalog  where id = #{id}"})
	@Results(value = {
	@Result(property = "id", column = "id"),
		@Result(property = "parentId", column = "parent_id"),
		@Result(property = "name", column = "name"),
		@Result(property = "thumb", column = "thumb"),
		@Result(property = "idx", column = "idx"),
		@Result(property = "keywords", column = "keywords"),
		@Result(property = "notes", column = "notes"),
	})
	PCatalog getById(Long id);

	// 列表
	@SelectProvider(type = PCatalogSqlProvider.class, method = "findList")
	List<PCatalog> findLists(@Param("param") ParamMap param);
	<T> List<T> findList(@Param("param") ParamMap param);
	// 分页列表
	@SelectProvider(type = PCatalogSqlProvider.class, method = "findListByPage")
	List<PCatalog> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);
	<T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

	/*================================查找===========================================*/
	// 增加
	@Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
	@Insert({"insert into p_catalog(id, parent_id, name, thumb, idx, keywords, notes) ",
		"values(",
		"#{id}, #{parentId}, #{name}, #{thumb}, #{idx}, #{keywords}, #{notes}",
        ")" })
	int insert(PCatalog pCatalog);

	@InsertProvider(type = PCatalogSqlProvider.class, method = "insertSelective")
    int insertSelective(PCatalog pCatalog);

	/* 批量增加 */
	@Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
	@InsertProvider(type = PCatalogSqlProvider.class, method = "insertBatch")
	int insertBatch(@Param("list") List<PCatalog> pCatalogList);

	// 删除
	@Delete("delete from p_catalog where id = #{id}")
	int delete(Long id);

	// 修改
	//@Update({"update p_catalog set ",
	//		"parent_id=#{parentId}, name=#{name}, thumb=#{thumb}, idx=#{idx}, keywords=#{keywords}, notes=#{notes}"
	//		," where id = #{id}"})
	@UpdateProvider(type = PCatalogSqlProvider.class, method = "update")
	int update(PCatalog pCatalog);
}