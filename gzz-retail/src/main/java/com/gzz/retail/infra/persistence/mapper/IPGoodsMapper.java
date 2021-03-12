package com.gzz.retail.infra.persistence.mapper;


import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.provider.PGoodsSqlProvider;
import com.gzz.retail.infra.persistence.pojo.PGoods;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
@CacheNamespace //开启缓存
public interface IPGoodsMapper {
	/*================================查找===========================================*/
	// 根据主键id
	@Select({"select * from P_GOODS  where id = #{id}"})
	@Results(id="pGoodsMap",value = {
	@Result(property = "id", column = "id"),
		@Result(property = "catalogId", column = "catalog_id"),
		@Result(property = "code", column = "code"),
		@Result(property = "name", column = "name"),
		@Result(property = "barCode", column = "bar_code"),
		@Result(property = "brandId", column = "brand_id"),
		@Result(property = "thumb", column = "thumb"),
		@Result(property = "unit", column = "unit"),
		@Result(property = "spec", column = "spec"),
		@Result(property = "idx", column = "idx"),
		@Result(property = "salePrice", column = "sale_price"),
		@Result(property = "marketPrice", column = "market_price"),
		@Result(property = "pv", column = "pv"),
		@Result(property = "status", column = "status"),
		@Result(property = "averageCost", column = "average_cost"),
	})
	PGoods getById(Long id);

	// 列表
	@ResultMap("pGoodsMap")
	@SelectProvider(type = PGoodsSqlProvider.class, method = "findList")
	List<PGoods> findLists(@Param("param") ParamMap param );
	@ResultMap("pGoodsMap")
    @SelectProvider(type = PGoodsSqlProvider.class, method = "findList")
	<T> List<T> findList(@Param("param") ParamMap param);
	// 分页列表
	@ResultMap("pGoodsMap")
	@SelectProvider(type = PGoodsSqlProvider.class, method = "findListByPage")
	List<PGoods> findListByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);
	@ResultMap("pGoodsMap")
    @SelectProvider(type = PGoodsSqlProvider.class, method = "findListByPage")
	<T> List<T> findListsByPage(@Param("param") ParamMap param, @Param("pager") Pager pager);

	/*================================查找===========================================*/
	// 增加
	@Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
	@Insert({"insert into P_GOODS(id, catalog_id, code, name, bar_code, brand_id, ",
			"thumb, unit, spec, idx, sale_price, market_price, pv, status, average_cost) ",
		"values(",
		"#{id, jdbcType=BIGINT}, #{catalogId, jdbcType=BIGINT}, #{code,jdbcType=VARCHAR}, ",
			"#{name,jdbcType=VARCHAR}, #{barCode,jdbcType=VARCHAR}, #{brandId,jdbcType=BIGINT},",
			" #{thumb,jdbcType=VARCHAR}, #{unit,jdbcType=VARCHAR}, #{spec,jdbcType=VARCHAR}, ",
			" #{idx,jdbcType=TINYINT}, #{salePrice,jdbcType=VARCHAR}, #{marketPrice,jdbcType=VARCHAR},",
			" #{pv,jdbcType=VARCHAR}, #{status, jdbcType=TINYINT}, #{averageCost,jdbcType=DECIMAL}",
        ")" })
	int insert(PGoods pGoods);

	@InsertProvider(type = PGoodsSqlProvider.class, method = "dynamicInsert")
    int dynamicInsert(PGoods pGoods);

	/* 批量增加 */
	@Options(useGeneratedKeys = true, keyProperty = "id") // 主键自增,默认主键名为id
	@InsertProvider(type = PGoodsSqlProvider.class, method = "batchInsert")
	int batchInsert(@Param("list") List<PGoods> pGoodsList);

	// 删除
	@Delete("delete from P_GOODS where id = #{id}")
	int delete(Long id);

	// 修改
	//@Update({"update P_GOODS set ",
	//		"catalog_id=#{catalogId,jdbcType=BIGINT}, code=#{code,jdbcType=VARCHAR}, name=#{name,jdbcType=VARCHAR}, bar_code=#{barCode,jdbcType=VARCHAR}, brand_id=#{brandId,jdbcType=BIGINT}, thumb=#{thumb,jdbcType=VARCHAR}, unit=#{unit,jdbcType=VARCHAR}, spec=#{spec,jdbcType=VARCHAR}, idx=#{idx,jdbcType=TINYINT}, sale_price=#{salePrice,jdbcType=VARCHAR}, market_price=#{marketPrice,jdbcType=VARCHAR}, pv=#{pv,jdbcType=VARCHAR}, status=#{status,jdbcType=TINYINT}, average_cost=#{averageCost,jdbcType=DECIMAL}"
	//		," where id = #{id}"})
	@UpdateProvider(type = PGoodsSqlProvider.class, method = "dynamicUpdate")
	int update(PGoods pGoods);
}