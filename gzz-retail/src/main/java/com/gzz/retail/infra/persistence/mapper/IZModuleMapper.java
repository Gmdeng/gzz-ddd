package com.gzz.retail.infra.persistence.mapper;

import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.ZModuleSqlProvider;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.cache.decorators.FifoCache;

import java.util.List;

/**
 * eviction:
 *     LRU – 最近最少使用的:移除最长时间不被使用的对象。
 *     FIFO – 先进先出:按对象进入缓存的顺序来移除它们。
 *     SOFT – 软引用:移除基于垃圾回收器状态和软引用规则的对象。
 *     WEAK – 弱引用:更积极地移除基于垃圾收集器状态和弱引用规则的对象。
 * flushInterval:
 *      每隔 60 秒刷新,
 * size:
 *      存数结果对象或列表的 512 个引用
 */
@Mapper
@CacheNamespace(flushInterval = 60000, size = 512, eviction = FifoCache.class, readWrite = true) //开启缓存
public interface IZModuleMapper {
    /*================================查找===========================================*/

    // 根据主键id
//    @Case(
//    value ="org.mybatis.caches.ehcache.EhcacheCache",
//    )
    // @Options(useCache = false)  // 禁用二级缓存
    @Select({"select * from Z_MODULE  where id = #{id}"})
    @Results(id = "zModuleMap", value = {
            @Result(property = "id", column = "id"),
            @Result(property = "parentId", column = "parent_id"),
            @Result(property = "type", column = "type"),
            @Result(property = "name", column = "name"),
            @Result(property = "code", column = "code"),
            @Result(property = "operate", column = "operate"),
            @Result(property = "icon", column = "icon"),
            @Result(property = "url", column = "url"),
            @Result(property = "idx", column = "idx"),
            @Result(property = "status", column = "status"),
            @Result(property = "updateOn", column = "update_on"),
            @Result(property = "updateBy", column = "update_by"),
            @Result(property = "createOn", column = "create_on"),
            @Result(property = "createBy", column = "create_by")
    })
    ZModulePo getById(Long id);

    // 列表

    @ResultMap("zModuleMap")
    @SelectProvider(type = ZModuleSqlProvider.class, method = "findList")
    List<ZModulePo> findLists(@Param("param") ParamMap param);


    @ResultMap("zModuleMap")
    @SelectProvider(type = ZModuleSqlProvider.class, method = "findList")
    <T> List<T> findList(@Param("param") ParamMap param);


    // 分页列表
    @Options(useCache = false) //禁用当前select语句的二级缓存
    @ResultMap("zModuleMap")
    @SelectProvider(type = ZModuleSqlProvider.class, method = "findListByPage")
    List<ZModulePo> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    //flushCache="true" 属性，默认状况下为true即刷新缓存，若是改为false则不会刷新
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @ResultMap("zModuleMap")
    @SelectProvider(type = ZModuleSqlProvider.class, method = "findListByPage")
    <T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

    /*================================查找===========================================*/
    // @SelectKey(statement = "SELECT LAST_INSERT_ID()", keyProperty = "id", resultType = Integer.class, before = false)    //返回插入的id，放入entity入参中
    //    @Options(useGeneratedKeys = true , keyProperty = "id")    //只能再自增id中用，返回插入的id，放入entity入参中
    // 增加
    @Options(useGeneratedKeys = true, keyProperty = "id", flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
    @Insert({"insert into Z_MODULE(id, parent_id, type, name, code, operate, icon, url, idx,",
            " status, update_on, update_by, create_on, create_by) ",
            "values(",
            "#{id,jdbcType=BIGINT}, #{parentId,jdbcType=BIGINT}, #{type,jdbcType=CHAR}, ",
            "#{name,jdbcType=VARCHAR}, #{code,jdbcType=VARCHAR},",
            "#{operate, jdbcType=TINYINT}, ",
            "#{icon,jdbcType=VARCHAR}, #{url,jdbcType=VARCHAR}, #{idx,jdbcType=TINYINT},",
            "#{status,jdbcType=TINYINT}, #{updateOn,jdbcType=TIMESTAMP}, ",
            "#{updateBy,jdbcType=VARCHAR}, #{createOn,jdbcType=TIMESTAMP}, ",
            "#{createBy,jdbcType=VARCHAR}",
            ")"})
    int insert(ZModulePo zModule);
    /* 动态增加 */
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @InsertProvider(type = ZModuleSqlProvider.class, method = "dynamicInsert")
    int dynamicInsert(ZModulePo zModule);

    /* 批量增加 */
    @Options(useGeneratedKeys = true, keyProperty = "id", flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
    @InsertProvider(type = ZModuleSqlProvider.class, method = "batchInsert")
    int batchInsert(@Param("list") List<ZModulePo> zModuleList);

    // 删除
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Delete("delete from Z_MODULE where id = #{id}")
    int delete(Long id);

    // 修改
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @UpdateProvider(type = ZModuleSqlProvider.class, method = "dynamicUpdate")
    int update(ZModulePo zModule);

}