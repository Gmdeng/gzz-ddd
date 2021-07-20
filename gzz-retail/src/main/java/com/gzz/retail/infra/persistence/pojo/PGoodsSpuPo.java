package com.gzz.retail.infra.persistence.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
/**
 * 商品SPU 实体类
 * @author Auto-Builder
 * @CrateOn 2021-7-20 16:36:44
 */
@Data
public class PGoodsSpuPo implements Serializable  {
	/**
	 *  id id
	 */
	private Long id;
	/**
	 *  分类ID catalogId
	 */
	private Long catalogId;
	/**
	 *  商品名称 name
	 */
	private String name;
	/**
	 *  商品编码 code
	 */
	private String code;
	/**
	 *  商品条码 barCode
	 */
	private String barCode;
	/**
	 *  品牌ID brandId
	 */
	private Long brandId;
	/**
	 *  缩略图 thumb
	 */
	private String thumb;
	/**
	 *  单位 unit
	 */
	private String unit;
	/**
	 *  规格参数JSON specsOwn
	 */
	private String specsOwn;
	/**
	 *  排序 idx
	 */
	private Byte idx;
	/**
	 *  销售价 price
	 */
	private BigDecimal price;
	/**
	 *  价值  pv
	 */
	private BigDecimal pv;
	/**
	 *  销售状态 status
	 */
	private Byte status;
	/**
	 *  商品加权平均成本 averageCost
	 */
	private BigDecimal averageCost;
	/**
	 *  运费 freight
	 */
	private BigDecimal freight;
}