package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.PAttributeSqlProvider;
import com.gzz.retail.infra.persistence.mapper.provider.ZRoleSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PAttributeOptionPo;
import com.gzz.retail.infra.persistence.pojo.PAttributePo;
import com.gzz.retail.infra.persistence.pojo.ZRolePermissionPo;
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
public interface IPAttributeMapper {
	/*================================查找===========================================*/
	// 根据主键id
	@Options(useCache = false)  // 禁用二级缓存
	@Select({"select * from P_ATTRIBUTE  where id = #{id}"})
	@Results(id="pAttributeMap",value = {
	@Result(property = "id", column = "id"),
		@Result(property = "catalogId", column = "catalog_id"),
		@Result(property = "catalogName", column = "catalog_name"),
		@Result(property = "name", column = "name"),
		@Result(property = "values", column = "values"),
		@Result(property = "idx", column = "idx"),
	})
	PAttributePo getById(Long id);

	// 列表
	@ResultMap("pAttributeMap")
	@SelectProvider(type = PAttributeSqlProvider.class, method = "findList")
	List<PAttributePo> findLists(@Param("param") ParamMap param);
	@ResultMap("pAttributeMap")
    @SelectProvider(type = PAttributeSqlProvider.class, method = "findList")
	<T> List<T> findList(@Param("param") ParamMap param);
	// 分页列表
	@ResultMap("pAttributeMap")
	@SelectProvider(type = PAttributeSqlProvider.class, method = "findListByPage")
	List<PAttributePo> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);
	@ResultMap("pAttributeMap")
    @SelectProvider(type = PAttributeSqlProvider.class, method = "findListByPage")
	<T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

	/*================================增加===========================================*/
	// 增加
	@Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@Insert({"insert into P_ATTRIBUTE(id, catalog_id, name, idx) ",
		"values(",
		"#{id,jdbcType=BIGINT}, #{catalogId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, #{idx,jdbcType=TINYINT}",
        ")" })
	int insert(PAttributePo pAttribute);

    @Options(flushCache = Options.FlushCachePolicy.TRUE) //刷新缓存
	@InsertProvider(type = PAttributeSqlProvider.class, method = "dynamicInsert")
    int dynamicInsert(PAttributePo pAttribute);

	/* 批量增加 */
	@Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@InsertProvider(type = PAttributeSqlProvider.class, method = "batchInsert")
	int batchInsert(@Param("list") List<PAttributePo> pAttributeList);


    /*================================修改===========================================*/
	// 修改
	//@Update({"update P_ATTRIBUTE set ",
	//		"catalog_id=#{catalogId,jdbcType=BIGINT}, name=#{name,jdbcType=VARCHAR}, values=#{values,jdbcType=VARCHAR}, idx=#{idx,jdbcType=TINYINT}"
	//		," where id = #{id}"})
	@Options(flushCache = Options.FlushCachePolicy.TRUE)
	@UpdateProvider(type = PAttributeSqlProvider.class, method = "dynamicUpdate")
	int update(PAttributePo pAttribute);

	/*================================删除===========================================*/
	// 删除
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Delete("delete from P_ATTRIBUTE where id = #{id}")
    int delete(Long id);

	/*================================选项处理===========================================*/
	// 清空选项
	@Options(flushCache = Options.FlushCachePolicy.TRUE)
	@Delete("delete from P_ATTRIBUTE_OPTION where attr_id = #{attrId}")
	int clearOptions(Long attrId);

	// 获取选项
	@Results(id="pAttributeOptionMap",value = {
			@Result(property = "id", column = "id"),
			@Result(property = "attrId", column = "attr_id"),
			@Result(property = "name", column = "name"),
			@Result(property = "notes", column = "notes")
	})
	@Select("select * from P_ATTRIBUTE_OPTION where attr_id = #{attrId}")
	List<PAttributeOptionPo> findOptions(Long attrId);

	// 批量增加选项
	@Options(useGeneratedKeys = true, keyProperty = "id", flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@InsertProvider(type = PAttributeSqlProvider.class, method = "batchInsertOptions")
	int batchInsertOption(@Param("dataList") List<PAttributeOptionPo> pAttributeOptionList);
}