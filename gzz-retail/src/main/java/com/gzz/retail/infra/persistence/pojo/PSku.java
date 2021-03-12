package com.gzz.retail.infra.persistence.pojo;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 *  实体类
 * @author Auto-Builder
 * @CrateOn 2020-10-10 11:01:14
 */
@Data
@Accessors
public class PSku  {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  商品ID goodsId
	 */
	private Long goodsId;
	/**
	 *  属性集 attrSet
	 */
	private String attrSet;
	/**
	 *  销售价 salePrice
	 */
	private String salePrice;
	/**
	 *  销售状态 0 status
	 */
	private Byte status;
	/**
	 *  缩略图 thumb
	 */
	private String thumb;
	/**
	 *  价值 pv
	 */
	private String pv;
}