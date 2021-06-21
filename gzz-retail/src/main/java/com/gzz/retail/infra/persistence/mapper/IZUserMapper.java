package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.ZUserSqlProvider;
import com.gzz.retail.infra.persistence.pojo.ZRolePo;
import com.gzz.retail.infra.persistence.pojo.ZUserPo;
import com.gzz.retail.infra.persistence.pojo.ZUserRoles;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@CacheNamespace //开启缓存
public interface IZUserMapper {
    /*================================查找===========================================*/
    // 根据主键id
    @Select({"select * form z_user  where id = #{id}"})
    @Results(id="zUserMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "passwd", column = "passwd"),
            @Result(property = "salt", column = "salt"),
            @Result(property = "petName", column = "pet_name"),
            @Result(property = "mobile", column = "mobile"),
            @Result(property = "allowIpaddr", column = "allow_ipaddr"),
            @Result(property = "notes", column = "notes"),
            @Result(property = "status", column = "status"),
            @Result(property = "updateOn", column = "update_on"),
            @Result(property = "updateBy", column = "update_by"),
            @Result(property = "createOn", column = "create_on"),
            @Result(property = "createBy", column = "create_by"),
    })
    ZUserPo getById(Long id);

    // 列表
    @ResultMap("zUserMap")
    @SelectProvider(type = ZUserSqlProvider.class, method = "findList")
    List<ZUserPo> findLists(@Param("param") ParamMap param);
    @ResultMap("zUserMap")
    @SelectProvider(type = ZUserSqlProvider.class, method = "findList")
    <T> List<T> findList(@Param("param") ParamMap param);

    // 分页列表
    @ResultMap("zUserMap")
    @SelectProvider(type = ZUserSqlProvider.class, method = "findListByPage")
    List<ZUserPo> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    @ResultMap("zUserMap")
    @SelectProvider(type = ZUserSqlProvider.class, method = "findListByPage")
    <T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    /*================================查找===========================================*/
    // 增加
    @Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
    @Insert({"insert into z_user(user_id, passwd, salt, pet_name, mobile, email, allow_ipaddr,deny_ipaddr,",
            "notes, status, update_on, update_by, create_on, create_by) ",
            "values(",
            " #{userId,jdbcType=VARCHAR }, #{passwd,jdbcType=VARCHAR}, #{salt,jdbcType=VARCHAR}, #{petName,jdbcType=VARCHAR},",
            " #{mobile,jdbcType=VARCHAR}, #{email,jdbcType=VARCHAR}, #{allowIpaddr,jdbcType=VARCHAR}, #{denyIpaddr,jdbcType=VARCHAR}, #{notes,jdbcType=VARCHAR}, ",
            " #{status,jdbcType=TINYINT, typeHandler=com}, ",
            " #{updateOn,jdbcType=TIMESTAMP}, #{updateBy,jdbcType=VARCHAR}, ",
            " #{createOn,jdbcType=TIMESTAMP}, #{createBy,jdbcType=VARCHAR}",
            ")"})
    int insert(ZUserPo zUser);

    @InsertProvider(type = ZUserSqlProvider.class, method = "insertSelective")
    int insertSelective(ZUserPo zUser);

    // 删除
    @Delete("delete from z_user where id = #{id}")
    int delete(Long id);

    // 修改
    @UpdateProvider(type = ZUserSqlProvider.class, method = "update")
    int update(ZUserPo zUser);

    /* 批量增加 */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @InsertProvider(type = ZUserSqlProvider.class, method = "insertBatch")
    int insertBatch(@Param("list") List<ZUserPo> zUserList);

    // 角色列表
    @Select("select r.* from z_role r, z_user_roles a where r.id = a.role_id where a.user_id=#{userId}")
    List<ZRolePo> findRoleLists(Long userId);

    /* 批量增加 */
    @Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
    @InsertProvider(type = ZUserSqlProvider.class, method = "insertBatchRole")
    int insertBatchRole(@Param("list") List<ZUserRoles> zUserRolesList);

    // 清除用户角色
    @Delete("delete from z_user_roles where user_id = #{userId}")
    int cleanRoles(Long userId);
}