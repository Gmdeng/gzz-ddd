package com.gzz.retail.infra.persistence.pojo;
import lombok.Data;
import lombok.experimental.Accessors;
/**
 *  实体类
 * @author Auto-Builder
 * @CrateOn 2020-10-10 11:01:23
 */
@Data
@Accessors
public class PGoodsAttr  {
	/**
	 *  ID id
	 */
	private Long id;
	/**
	 *  goods_id goodsId
	 */
	private Long goodsId;
	/**
	 *  属性名称 name
	 */
	private String name;
	/**
	 *  属性值项，用逗号隔里 values
	 */
	private String values;
}