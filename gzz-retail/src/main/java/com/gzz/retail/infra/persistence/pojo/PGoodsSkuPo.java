package com.gzz.retail.infra.persistence.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 商品SKU 实体类
 * @author Auto-Builder
 * @CrateOn 2021-7-20 16:36:29
 */
@Data
public class PGoodsSkuPo implements Serializable  {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  商品ID goodsId
	 */
	private Long goodsId;
	/**
	 *  属性集JSON attrsOwn
	 */
	private String attrsOwn;
	/**
	 *  属性ID用用＿串连 attrsIndexes
	 */
	private String attrsIndexes;
	/**
	 *  销售价 price
	 */
	private BigDecimal price;
	/**
	 *  价值 pv
	 */
	private BigDecimal pv;
	/**
	 *  销售状态 0 status
	 */
	private Byte status;
	/**
	 *  缩略图 thumb
	 */
	private String thumb;
}