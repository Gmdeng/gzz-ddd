package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.PAttributeOptionsSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PAttributeOptionsPo;
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
public interface IPAttributeOptionsMapper {
	/*================================查找===========================================*/
	// 根据主键id
	@Options(useCache = false)  // 禁用二级缓存
	@Select({"select * from P_ATTRIBUTE_OPTIONS  where id = #{id}"})
	@Results(id="pAttributeOptionsMap",value = {
		@Result(property = "id", column = "id"),
		@Result(property = "attrId", column = "attr_id"),
		@Result(property = "name", column = "name"),
	})
	PAttributeOptionsPo getById(Long id);

	// 列表
	@ResultMap("pAttributeOptionsMap")
	@SelectProvider(type = PAttributeOptionsSqlProvider.class, method = "findList")
	List<PAttributeOptionsPo> findLists(@Param("param") ParamMap param);
	@ResultMap("pAttributeOptionsMap")
    @SelectProvider(type = PAttributeOptionsSqlProvider.class, method = "findList")
	<T> List<T> findList(@Param("param") ParamMap param);
	// 分页列表
	@ResultMap("pAttributeOptionsMap")
	@SelectProvider(type = PAttributeOptionsSqlProvider.class, method = "findListByPage")
	List<PAttributeOptionsPo> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);
	@ResultMap("pAttributeOptionsMap")
    @SelectProvider(type = PAttributeOptionsSqlProvider.class, method = "findListByPage")
	<T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

	/*================================增加===========================================*/
	// 增加
	@Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@Insert({"insert into P_ATTRIBUTE_OPTIONS(id, attr_id, name) ",
		"values(",
		"#{id,jdbcType=BIGINT}, #{attrId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}",
        ")" })
	int insert(PAttributeOptionsPo pAttributeOptions);

    @Options(flushCache = Options.FlushCachePolicy.TRUE) //刷新缓存
	@InsertProvider(type = PAttributeOptionsSqlProvider.class, method = "dynamicInsert")
    int dynamicInsert(PAttributeOptionsPo pAttributeOptions);

	/* 批量增加 */
	@Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@InsertProvider(type = PAttributeOptionsSqlProvider.class, method = "batchInsert")
	int batchInsert(@Param("list") List<PAttributeOptionsPo> pAttributeOptionsList);


    /*================================修改===========================================*/
	// 修改
	//@Update({"update P_ATTRIBUTE_OPTIONS set ",
	//		"attr_id=#{attrId,jdbcType=BIGINT}, name=#{name,jdbcType=VARCHAR}"
	//		," where id = #{id}"})
	@Options(flushCache = Options.FlushCachePolicy.TRUE)
	@UpdateProvider(type = PAttributeOptionsSqlProvider.class, method = "dynamicUpdate")
	int update(PAttributeOptionsPo pAttributeOptions);

	/*================================删除===========================================*/
	// 删除
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Delete("delete from P_ATTRIBUTE_OPTIONS where id = #{id}")
    int delete(Long id);
}