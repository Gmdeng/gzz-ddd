package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.PGoodsSkuSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PGoodsSkuPo;
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
public interface IPGoodsSkuMapper {
	/*================================查找===========================================*/
	// 根据主键id
	@Options(useCache = false)  // 禁用二级缓存
	@Select({"select * from P_GOODS_SKU  where id = #{id}"})
	@Results(id="pGoodsSkuMap",value = {
	@Result(property = "id", column = "id"),
		@Result(property = "goodsId", column = "goods_id"),
		@Result(property = "attrsOwn", column = "attrs_own"),
		@Result(property = "attrsIndexes", column = "attrs_indexes"),
		@Result(property = "price", column = "price"),
		@Result(property = "pv", column = "pv"),
		@Result(property = "status", column = "status"),
		@Result(property = "thumb", column = "thumb"),
	})
	PGoodsSkuPo getById(Long id);

	// 列表
	@ResultMap("pGoodsSkuMap")
	@SelectProvider(type = PGoodsSkuSqlProvider.class, method = "findList")
	List<PGoodsSkuPo> findLists(@Param("param") ParamMap param);
	@ResultMap("pGoodsSkuMap")
    @SelectProvider(type = PGoodsSkuSqlProvider.class, method = "findList")
	<T> List<T> findList(@Param("param") ParamMap param);
	// 分页列表
	@ResultMap("pGoodsSkuMap")
	@SelectProvider(type = PGoodsSkuSqlProvider.class, method = "findListByPage")
	List<PGoodsSkuPo> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);
	@ResultMap("pGoodsSkuMap")
    @SelectProvider(type = PGoodsSkuSqlProvider.class, method = "findListByPage")
	<T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

	/*================================增加===========================================*/
	// 增加
	@Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@Insert({"insert into P_GOODS_SKU(id, goods_id, attrs_own, attrs_indexes, price, pv, status, thumb) ",
		"values(",
		"#{id,jdbcType=BIGINT}, #{goodsId,jdbcType=BIGINT}, #{attrsOwn,jdbcType=VARCHAR}, #{attrsIndexes,jdbcType=VARCHAR}, #{price,jdbcType=DECIMAL}, #{pv,jdbcType=DECIMAL}, #{status,jdbcType=TINYINT}, #{thumb,jdbcType=VARCHAR}",
        ")" })
	int insert(PGoodsSkuPo pGoodsSku);

    @Options(flushCache = Options.FlushCachePolicy.TRUE) //刷新缓存
	@InsertProvider(type = PGoodsSkuSqlProvider.class, method = "dynamicInsert")
    int dynamicInsert(PGoodsSkuPo pGoodsSku);

	/* 批量增加 */
	@Options(useGeneratedKeys = true, keyProperty = "id",flushCache = Options.FlushCachePolicy.TRUE) // 主键自增,默认主键名为id
	@InsertProvider(type = PGoodsSkuSqlProvider.class, method = "batchInsert")
	int batchInsert(@Param("list") List<PGoodsSkuPo> pGoodsSkuList);


    /*================================修改===========================================*/
	// 修改
	//@Update({"update P_GOODS_SKU set ",
	//		"goods_id=#{goodsId,jdbcType=BIGINT}, attrs_own=#{attrsOwn,jdbcType=VARCHAR}, attrs_indexes=#{attrsIndexes,jdbcType=VARCHAR}, price=#{price,jdbcType=DECIMAL}, pv=#{pv,jdbcType=DECIMAL}, status=#{status,jdbcType=TINYINT}, thumb=#{thumb,jdbcType=VARCHAR}"
	//		," where id = #{id}"})
	@Options(flushCache = Options.FlushCachePolicy.TRUE)
	@UpdateProvider(type = PGoodsSkuSqlProvider.class, method = "dynamicUpdate")
	int update(PGoodsSkuPo pGoodsSku);

	/*================================删除===========================================*/
	// 删除
    @Options(flushCache = Options.FlushCachePolicy.TRUE)
    @Delete("delete from P_GOODS_SKU where id = #{id}")
    int delete(Long id);
}