package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.PGoodsSpuSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PGoodsSpuPo;
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
public interface IPGoodsSpuMapper {
	/*================================查找===========================================*/
	// 根据主键id
	@Options(useCache = false)  // 禁用二级缓存
	@Select({"select * from P_GOODS_SPU  where id = #{id}"})
	@Results(id="pGoodsSpuMap",value = {
		@Result(property = "id", column = "id"),
		@Result(property = "catalogId", column = "catalog_id"),
		@Result(property = "name", column = "name"),
		@Result(property = "code", column = "code"),
		@Result(property = "barCode", column = "bar_code"),
		@Result(property = "brandId", column = "brand_id"),
		@Result(property = "thumb", column = "thumb"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "specsOwn", column = "specs_own"),
		@Result(property = "idx", column = "idx"),
		@Result(property = "price", column = "price"),
		@Result(property = "pv", column = "pv"),
		@Result(property = "status", column = "status"),
		@Result(property = "averageCost", column = "average_cost"),
		@Result(property = "freight", column = "freight"),
	})
	PGoodsSpuPo getById(Long id);

	// 列表
	@ResultMap("pGoodsSpuMap")
	@SelectProvider(type = PGoodsSpuSqlProvider.class, method = "findList")
	List<PGoodsSpuPo> findLists(@Param("param") ParamMap param);
	@ResultMap("pGoodsSpuMap")
    @SelectProvider(type = PGoodsSpuSqlProvider.class, method = "findList")
	<T> List<T> findList(@Param("param") ParamMap param);
	// 分页列表
	@ResultMap("pGoodsSpuMap")
	@SelectProvider(type = PGoodsSpuSqlProvider.class, method = "findListByPage")
	List<PGoodsSpuPo> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);
	@ResultMap("pGoodsSpuMap")
    @SelectProvider(type = PGoodsSpuSqlProvider.class, method = "findListByPage")
	<T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

	/*================================增加===========================================*/
	// 增加
	@Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@Insert({"insert into P_GOODS_SPU(catalog_id, name, code, bar_code, brand_id, thumb, unit,",
			"specs_own, idx, price, pv, status, average_cost, freight) ",
			"values(",
			"#{catalogId,jdbcType=BIGINT}, #{name,jdbcType=VARCHAR}, #{code,jdbcType=CHAR}, ",
			"#{barCode,jdbcType=VARCHAR}, #{brandId,jdbcType=BIGINT}, #{thumb,jdbcType=VARCHAR},",
			"#{unit,jdbcType=VARCHAR}, #{specsOwn,jdbcType=VARCHAR}, #{idx,jdbcType=TINYINT},",
			"#{price,jdbcType=DECIMAL}, #{pv,jdbcType=DECIMAL}, #{status,jdbcType=TINYINT},",
			"#{averageCost,jdbcType=DECIMAL}, #{freight,jdbcType=DECIMAL}",
        ")" })
	int insert(PGoodsSpuPo pGoodsSpu);

    @Options(flushCache = Options.FlushCachePolicy.TRUE) //刷新缓存
	@InsertProvider(type = PGoodsSpuSqlProvider.class, method = "dynamicInsert")
    int dynamicInsert(PGoodsSpuPo pGoodsSpu);

	/* 批量增加 */
	@Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@InsertProvider(type = PGoodsSpuSqlProvider.class, method = "batchInsert")
	int batchInsert(@Param("list") List<PGoodsSpuPo> pGoodsSpuList);


    /*================================修改===========================================*/
	// 修改
	//@Update({"update P_GOODS_SPU set ",
	//		"catalog_id=#{catalogId,jdbcType=BIGINT}, name=#{name,jdbcType=VARCHAR}, code=#{code,jdbcType=CHAR}, bar_code=#{barCode,jdbcType=VARCHAR}, brand_id=#{brandId,jdbcType=BIGINT}, thumb=#{thumb,jdbcType=VARCHAR}, unit=#{unit,jdbcType=VARCHAR}, specs_own=#{specsOwn,jdbcType=VARCHAR}, idx=#{idx,jdbcType=TINYINT}, price=#{price,jdbcType=DECIMAL}, pv=#{pv,jdbcType=DECIMAL}, status=#{status,jdbcType=TINYINT}, average_cost=#{averageCost,jdbcType=DECIMAL}, freight=#{freight,jdbcType=DECIMAL}"
	//		," where id = #{id}"})
	@Options(flushCache = Options.FlushCachePolicy.TRUE)
	@UpdateProvider(type = PGoodsSpuSqlProvider.class, method = "dynamicUpdate")
	int update(PGoodsSpuPo pGoodsSpu);

	/*================================删除===========================================*/
	// 删除
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Delete("delete from P_GOODS_SPU where id = #{id}")
    int delete(Long id);
}