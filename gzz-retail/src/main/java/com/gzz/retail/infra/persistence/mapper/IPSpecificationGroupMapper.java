package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.PSpecificationGroupSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PSpecificationGroupPo;
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
public interface IPSpecificationGroupMapper {
	/*================================查找===========================================*/
	// 根据主键id
	@Options(useCache = false)  // 禁用二级缓存
	@Select({"select * from P_SPECIFICATION_GROUP  where id = #{id}"})
	@Results(id="pSpecificationGroupMap",value = {
	@Result(property = "id", column = "id"),
		@Result(property = "name", column = "name"),
	})
	PSpecificationGroupPo getById(Long id);

	// 列表
	@ResultMap("pSpecificationGroupMap")
	@SelectProvider(type = PSpecificationGroupSqlProvider.class, method = "findList")
	List<PSpecificationGroupPo> findLists(@Param("param") ParamMap param);
	@ResultMap("pSpecificationGroupMap")
    @SelectProvider(type = PSpecificationGroupSqlProvider.class, method = "findList")
	<T> List<T> findList(@Param("param") ParamMap param);
	// 分页列表
	@ResultMap("pSpecificationGroupMap")
	@SelectProvider(type = PSpecificationGroupSqlProvider.class, method = "findListByPage")
	List<PSpecificationGroupPo> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);
	@ResultMap("pSpecificationGroupMap")
    @SelectProvider(type = PSpecificationGroupSqlProvider.class, method = "findListByPage")
	<T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

	/*================================增加===========================================*/
	// 增加
	@Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@Insert({"insert into P_SPECIFICATION_GROUP(id, name) ",
		"values(",
		"#{id,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}",
        ")" })
	int insert(PSpecificationGroupPo pSpecificationGroup);

    @Options(flushCache = Options.FlushCachePolicy.TRUE) //刷新缓存
	@InsertProvider(type = PSpecificationGroupSqlProvider.class, method = "dynamicInsert")
    int dynamicInsert(PSpecificationGroupPo pSpecificationGroup);

	/* 批量增加 */
	@Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@InsertProvider(type = PSpecificationGroupSqlProvider.class, method = "batchInsert")
	int batchInsert(@Param("list") List<PSpecificationGroupPo> pSpecificationGroupList);


    /*================================修改===========================================*/
	// 修改
	//@Update({"update P_SPECIFICATION_GROUP set ",
	//		"name=#{name,jdbcType=VARCHAR}"
	//		," where id = #{id}"})
	@Options(flushCache = Options.FlushCachePolicy.TRUE)
	@UpdateProvider(type = PSpecificationGroupSqlProvider.class, method = "dynamicUpdate")
	int update(PSpecificationGroupPo pSpecificationGroup);

	/*================================删除===========================================*/
	// 删除
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Delete("delete from P_SPECIFICATION_GROUP where id = #{id}")
    int delete(Long id);
}