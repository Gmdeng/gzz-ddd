package com.gzz.retail.infra.persistence.mapper;


import com.gzz.retail.infra.persistence.mapper.provider.ZRoleSqlProvider;
import com.gzz.retail.infra.persistence.pojo.ZRole;
import com.gzz.retail.infra.persistence.pojo.ZRoleAuthority;
import org.apache.ibatis.annotations.*;

import java.util.List;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;

@Mapper
@CacheNamespace //开启缓存
public interface IZRoleMapper {
	/*================================查找===========================================*/
	// 根据主键id
	@Select({"select * form z_role  where id = #{id}"})
	@Results(value = {
	@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
		@Result(property = "code", column = "code"),
		@Result(property = "idx", column = "idx"),
		@Result(property = "status", column = "status"),
		@Result(property = "updateOn", column = "update_on"),
		@Result(property = "updateBy", column = "update_by"),
		@Result(property = "createOn", column = "create_on"),
		@Result(property = "createBy", column = "create_by"),
	})
	ZRole getById(Long id);

	// 列表
	@SelectProvider(type = ZRoleSqlProvider.class, method = "findList")
	List<ZRole> findLists(@Param("param") ParamMap param);
	<T> List<T> findList(@Param("param") ParamMap param);


	// 分页列表
	@SelectProvider(type = ZRoleSqlProvider.class, method = "findListByPage")
	List<ZRole> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);
	<T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

	/*================================查找===========================================*/
	// 增加
	@Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
	@Insert({"insert into z_role(id, name, code, idx, status, update_on, update_by, create_on, create_by) ",
		"values(",
		"#{id}, #{name}, #{code}, #{idx}, #{status}, #{updateOn}, #{updateBy}, #{createOn}, #{createBy}",
        ")" })
	int insert(ZRole zRole);

	@InsertProvider(type = ZRoleSqlProvider.class, method = "insertSelective")
    int insertSelective(ZRole zRole);

	// 权限列表
	@Select("select * from z_role_authority where role_id = #{roleId}")
	List<ZRoleAuthority> findAuthorityLists(Long roleId);
	/* 批量增加权限 */
	@Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
	@InsertProvider(type = ZRoleSqlProvider.class, method = "insertBatchAuthority")
	int insertBatch(@Param("list") List<ZRoleAuthority> zRoleAuthorityList);
	// 删除所有权限
	@Delete("delete from z_role_authority where id = #{roleId}")
	int clearAuthority(Long roleId);

	// 删除
	@Delete("delete from z_role where id = #{id}")
	int delete(Long id);


	// 修改
	//@Update({"update z_role set ",
	//		"name=#{name}, code=#{code}, idx=#{idx}, status=#{status}, update_on=#{updateOn}, update_by=#{updateBy}, create_on=#{createOn}, create_by=#{createBy}"
	//		," where id = #{id}"})
	@UpdateProvider(type = ZRoleSqlProvider.class, method = "update")
	int update(ZRole zRole);
}