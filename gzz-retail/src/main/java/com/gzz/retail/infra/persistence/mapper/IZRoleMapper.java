package com.gzz.retail.infra.persistence.mapper;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.ZRoleSqlProvider;
import com.gzz.retail.infra.persistence.pojo.ZRolePermissionPo;
import com.gzz.retail.infra.persistence.pojo.ZRolePo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.FifoCache;

import java.util.List;

/**
 * 开启缓存
 * eviction:
 *     LRU – 最近最少使用的:移除最长时间不被使用的对象。
 *     FIFO – 先进先出:按对象进入缓存的顺序来移除它们。
 *     SOFT – 软引用:移除基于垃圾回收器状态和软引用规则的对象。
 *     WEAK – 弱引用:更积极地移除基于垃圾收集器状态和弱引用规则的对象。
 * flushInterval:
 *      每隔 60 秒刷新,
 * size:
 *      存数结果对象或列表的 512 个引用， 默认值是1024
 * readWrite:
 *      可读写的缓存会返回缓存对象的拷贝（通过序列化）。这会慢一些，但是安全，因此默认是true
 *      只读的缓存会给所有调用者返回缓存对象的相同实例。因此这些对象不能被修改。这提供了很重要的性能优势
 */
@Mapper
@CacheNamespace(flushInterval = 60000, size = 512, eviction = FifoCache.class, readWrite = true) //开启缓存
public interface IZRoleMapper {
    /*================================查找===========================================*/
    // 根据主键id
    @Options(useCache = false)  // 禁用二级缓存
    @Select({"select * from Z_ROLE  where id = #{id}"})
    @Results(id="zRoleMap",value = {
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "code", column = "code"),
            @Result(property = "idx", column = "idx"),
            @Result(property = "status", column = "status"),
            @Result(property = "updateOn", column = "update_on"),
            @Result(property = "updateBy", column = "update_by"),
            @Result(property = "createOn", column = "create_on"),
            @Result(property = "createBy", column = "create_by"),
            @Result(property = "notes", column = "notes"),
    })
    ZRolePo getById(Long id);

    // 列表
    @ResultMap("zRoleMap")
    @SelectProvider(type = ZRoleSqlProvider.class, method = "findList")
    List<ZRolePo> findLists(@Param("param") ParamMap param );
    @ResultMap("zRoleMap")
    @SelectProvider(type = ZRoleSqlProvider.class, method = "findList")
    <T> List<T> findList(@Param("param") ParamMap param);
    // 分页列表
    @ResultMap("zRoleMap")
    @SelectProvider(type = ZRoleSqlProvider.class, method = "findListByPage")
    List<ZRolePo> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);
    @ResultMap("zRoleMap")
    @SelectProvider(type = ZRoleSqlProvider.class, method = "findListByPage")
    <T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    /*================================查找===========================================*/
    // 增加
    @Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
    @Insert({"insert into Z_ROLE( name, code, idx, status, update_on, update_by, create_on, create_by, notes) ",
            "values(",
            " #{name,jdbcType=VARCHAR}, #{code,jdbcType=VARCHAR}, #{idx,jdbcType=TINYINT}, #{status,jdbcType=TINYINT}, #{updateOn,jdbcType=TIMESTAMP}, #{updateBy,jdbcType=VARCHAR}, #{createOn,jdbcType=TIMESTAMP}, #{createBy,jdbcType=VARCHAR}, #{notes,jdbcType=VARCHAR}",
            ")" })
    int insert(ZRolePo zRole);

    @Options(flushCache = Options.FlushCachePolicy.TRUE) //刷新缓存
    @InsertProvider(type = ZRoleSqlProvider.class, method = "dynamicInsert")
    int dynamicInsert(ZRolePo zRole);

    /* 批量增加 */
    @Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
    @InsertProvider(type = ZRoleSqlProvider.class, method = "batchInsert")
    int batchInsert(@Param("list") List<ZRolePo> zRoleList);

    // 删除
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Delete("delete from Z_ROLE where id = #{id}")
    int delete(Long id);

    // 修改
    //@Update({"update Z_ROLE set ",
    //		"name=#{name,jdbcType=VARCHAR}, code=#{code,jdbcType=VARCHAR}, idx=#{idx,jdbcType=TINYINT}, status=#{status,jdbcType=TINYINT}, update_on=#{updateOn,jdbcType=TIMESTAMP}, update_by=#{updateBy,jdbcType=VARCHAR}, create_on=#{createOn,jdbcType=TIMESTAMP}, create_by=#{createBy,jdbcType=VARCHAR}, notes=#{notes,jdbcType=VARCHAR}"
    //		," where id = #{id}"})
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @UpdateProvider(type = ZRoleSqlProvider.class, method = "dynamicUpdate")
    int update(ZRolePo zRole);

    // 清空许可
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Delete("delete from Z_ROLE_PERMISSION where role_id = #{roleId}")
    int clearPermissions(Long roleId);

    // 获取许可
    @Results(id="zRolePermissionMap",value = {
            @Result(property = "id", column = "id"),
            @Result(property = "roleId", column = "role_id"),
            @Result(property = "moduleId", column = "module_id"),
            @Result(property = "hasPower", column = "has_power"),
    })
    @Select("select * from Z_ROLE_PERMISSION where role_id = #{roleId}")
    List<ZRolePermissionPo> findPermissions(Long roleId);

    // 批量增加许可
    @Options(useGeneratedKeys = true, keyProperty = "id", flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
    @InsertProvider(type = ZRoleSqlProvider.class, method = "batchInsertPermission")
    int batchInsertPermission(@Param("dataList") List<ZRolePermissionPo> zRolePermissionList);
}