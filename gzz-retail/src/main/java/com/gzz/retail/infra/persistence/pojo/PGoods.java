package com.gzz.retail.infra.persistence.pojo;
import java.math.BigDecimal;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 *  实体类
 * @author Auto-Builder
 * @CrateOn 2021-1-14 23:37:32
 */
@Data
@Accessors
public class PGoods  {
	/**
	 *  id id
	 */
	private Long id;
	/**
	 *  分类ID catalogId
	 */
	private Long catalogId;
	/**
	 *  商品编码 code
	 */
	private String code;
	/**
	 *  商品名称 name
	 */
	private String name;
	/**
	 *  商品条码 barCode
	 */
	private String barCode;
	/**
	 *  brand_id brandId
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
	 *  spec spec
	 */
	private String spec;
	/**
	 *  排序 idx
	 */
	private Byte idx;
	/**
	 *  销售价 salePrice
	 */
	private String salePrice;
	/**
	 *  市场价 marketPrice
	 */
	private String marketPrice;
	/**
	 *  价值  pv
	 */
	private String pv;
	/**
	 *  销售状态 status
	 */
	private Byte status;
	/**
	 *  商品加权平均成本 averageCost
	 */
	private BigDecimal averageCost;
}