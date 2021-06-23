/*
SQLyog Ultimate v13.1.1 (64 bit)
MySQL - 8.0.13 : Database - gzz_retail
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`gzz_retail` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `gzz_retail`;

/*Table structure for table `a_article` */

DROP TABLE IF EXISTS `a_article`;

CREATE TABLE `a_article` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `catalog_id` bigint(20) NOT NULL COMMENT '分类ID',
  `tag_id` bigint(20) DEFAULT NULL COMMENT '标签',
  `title` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `idx` tinyint(4) DEFAULT NULL COMMENT '排序',
  `thumb` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '缩略图',
  `content` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '内容',
  `author` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '作者',
  `create_on` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `update_on` timestamp NOT NULL,
  `update_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文章';

/*Data for the table `a_article` */

/*Table structure for table `a_catalog` */

DROP TABLE IF EXISTS `a_catalog`;

CREATE TABLE `a_catalog` (
  `id` bigint(20) NOT NULL,
  `parent_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '上级ID',
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `thumb` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '缩略图',
  `idx` tinyint(4) DEFAULT NULL COMMENT '排序',
  `keywords` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关键字',
  `notes` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文章分类';

/*Data for the table `a_catalog` */

/*Table structure for table `a_tag` */

DROP TABLE IF EXISTS `a_tag`;

CREATE TABLE `a_tag` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `thumb` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '缩略图',
  `idx` tinyint(4) DEFAULT NULL COMMENT '排序',
  `keywords` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '关键字',
  `notes` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='文章标签';

/*Data for the table `a_tag` */

/*Table structure for table `b_sale_order` */

DROP TABLE IF EXISTS `b_sale_order`;

CREATE TABLE `b_sale_order` (
  `no` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `time_on` timestamp NOT NULL,
  `type` tinyint(4) DEFAULT NULL COMMENT '类型',
  `customer_no` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '会员编号',
  `customer_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '客户姓名',
  `qty` int(11) DEFAULT NULL COMMENT '商品数量',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '商品金额',
  `commission` decimal(10,2) DEFAULT NULL COMMENT '手续费',
  `freight` decimal(10,2) DEFAULT NULL COMMENT '运费',
  `tax` decimal(10,2) DEFAULT NULL COMMENT '税费',
  `status` tinyint(4) DEFAULT NULL,
  `comments` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '留言',
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `b_sale_order` */

/*Table structure for table `b_sale_order_detail` */

DROP TABLE IF EXISTS `b_sale_order_detail`;

CREATE TABLE `b_sale_order_detail` (
  `sale_no` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '销售单号',
  `goods_code` varchar(10) COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品编码',
  `goods_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `goods_thumb` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品缩图',
  `goods_id` bigint(20) DEFAULT NULL COMMENT '商品ID',
  `sku_id` bigint(20) DEFAULT NULL COMMENT '商品SKUID',
  `qty` int(11) DEFAULT NULL COMMENT '数量',
  `price` decimal(10,2) DEFAULT NULL COMMENT '单价',
  `pv` decimal(10,2) DEFAULT NULL COMMENT '积分',
  PRIMARY KEY (`sale_no`,`goods_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `b_sale_order_detail` */

/*Table structure for table `b_sale_order_log` */

DROP TABLE IF EXISTS `b_sale_order_log`;

CREATE TABLE `b_sale_order_log` (
  `id` bigint(20) NOT NULL,
  `sale_no` varchar(20) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '销售单号',
  `status_old` tinyint(4) DEFAULT NULL,
  `status_new` tinyint(4) DEFAULT NULL,
  `modify_on` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `b_sale_order_log` */

/*Table structure for table `b_ship_order` */

DROP TABLE IF EXISTS `b_ship_order`;

CREATE TABLE `b_ship_order` (
  `ship_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '物流单号',
  `ship_method` tinyint(4) DEFAULT NULL COMMENT '收货方式 0-快递， 1-自提',
  `ship_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货姓名',
  `ship_mobile` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货手机号',
  `ship_region` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货地区编码',
  `ship_address` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '收货地址',
  `sale_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '销售单号',
  `express_company` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '快递公司',
  `express_no` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '快递单号',
  `express_fee` decimal(10,2) DEFAULT NULL COMMENT '快递费用',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  PRIMARY KEY (`ship_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='物流信息';

/*Data for the table `b_ship_order` */

/*Table structure for table `b_ship_order_detail` */

DROP TABLE IF EXISTS `b_ship_order_detail`;

CREATE TABLE `b_ship_order_detail` (
  `ship_no` varchar(4) COLLATE utf8mb4_general_ci NOT NULL,
  `spu_code` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `spu_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `spu_thumb` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `spu_id` bigint(20) DEFAULT NULL,
  `sku_id` bigint(20) DEFAULT NULL,
  `qty` varchar(50) COLLATE utf8mb4_general_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`ship_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `b_ship_order_detail` */

/*Table structure for table `b_shopping_cart` */

DROP TABLE IF EXISTS `b_shopping_cart`;

CREATE TABLE `b_shopping_cart` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `goods_id` bigint(20) NOT NULL COMMENT '商品ID',
  `goods_code` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '商品编码',
  `goods_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `goods_thumb` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品缩图',
  `sku_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'SKUID',
  `sku_attrs` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'SKU属性集',
  `qty` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '数量',
  `price` decimal(10,2) DEFAULT NULL COMMENT '价格',
  `customer_id` bigint(20) DEFAULT NULL COMMENT '客户ID',
  `add_on` timestamp NOT NULL COMMENT '加入时间',
  `modify_on` timestamp NOT NULL COMMENT '修改时间',
  `no` varchar(0) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='购物车';

/*Data for the table `b_shopping_cart` */

/*Table structure for table `c_address` */

DROP TABLE IF EXISTS `c_address`;

CREATE TABLE `c_address` (
  `id` bigint(20) NOT NULL,
  `customer_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户编号',
  `area_code` char(6) COLLATE utf8mb4_general_ci NOT NULL COMMENT '地区编码',
  `address` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '地址',
  `zip_code` varchar(4) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮编号码',
  `mobile` varchar(11) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `tel` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电话',
  `email` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '电子邮件',
  `is_default` bit(1) NOT NULL COMMENT '是否为默认',
  `identity` char(2) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='地址信息';

/*Data for the table `c_address` */

/*Table structure for table `c_customer_authorize` */

DROP TABLE IF EXISTS `c_customer_authorize`;

CREATE TABLE `c_customer_authorize` (
  `id` bigint(20) NOT NULL,
  `customer_no` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `第三方账号` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `密码凭证` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `认证状态` tinyint(4) DEFAULT NULL,
  `绑定状态` tinyint(4) DEFAULT NULL,
  `解绑时间` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `绑定时间` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `创建时间` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `更新时间` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `授权类型` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `c_customer_authorize` */

/*Table structure for table `c_customer_login` */

DROP TABLE IF EXISTS `c_customer_login`;

CREATE TABLE `c_customer_login` (
  `customer_no` varchar(10) COLLATE utf8mb4_general_ci NOT NULL,
  `user_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '用户名',
  `passwd` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码',
  `salt` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '密码盐',
  `login_on` timestamp NULL DEFAULT NULL COMMENT '登录时间',
  `login_ipaddr` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录IP地址',
  `modify_on` timestamp NULL DEFAULT NULL COMMENT '修改密码时间',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `allow_ipaddrs` varchar(250) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '允许登录白名单IP',
  `deny_ipaddrs` varchar(250) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '拒绝登录黑名单IP',
  `login_type` tinyint(4) DEFAULT NULL COMMENT '登录类型',
  PRIMARY KEY (`customer_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用于登录账号';

/*Data for the table `c_customer_login` */

/*Table structure for table `c_favorite` */

DROP TABLE IF EXISTS `c_favorite`;

CREATE TABLE `c_favorite` (
  `id` bigint(20) NOT NULL,
  `customer_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户编号',
  `type` char(2) COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型：A-文章, P-商品 C-店铺',
  `link_id` bigint(20) NOT NULL COMMENT '对象ID',
  `time_on` timestamp NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='收藏';

/*Data for the table `c_favorite` */

/*Table structure for table `c_supplier` */

DROP TABLE IF EXISTS `c_supplier`;

CREATE TABLE `c_supplier` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `module_id` bigint(20) NOT NULL COMMENT '模块ID',
  `has_power` tinyint(4) NOT NULL COMMENT '拥有权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='供应商';

/*Data for the table `c_supplier` */

/*Table structure for table `c_supplier_license` */

DROP TABLE IF EXISTS `c_supplier_license`;

CREATE TABLE `c_supplier_license` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `营业执照注册号` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '营业执照注册号',
  `法人代表人姓名` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '法人代表人姓名',
  `营业执照所在地` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '营业执照所在地',
  `企业注册资金` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业注册资金',
  `营业执照有效期` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '营业执照有效期',
  `公司地址` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '公司地址',
  `营业执照经营范围` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '营业执照经营范围LICENSE_SCOPE',
  `企业名称` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '企业名称',
  `联系人` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '联系人',
  `介绍内容` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '介绍内容',
  `是否已交保证金` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '是否已交保证金',
  `质量保证金` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '质量保证金',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='供应商证照';

/*Data for the table `c_supplier_license` */

/*Table structure for table `e_coupon` */

DROP TABLE IF EXISTS `e_coupon`;

CREATE TABLE `e_coupon` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `title` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '主题名称',
  `value` int(11) DEFAULT NULL COMMENT '面值',
  `type` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '现金券， 折扣券',
  `distribution_mode` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发放方式，1-自行领取. 2-赠送，3-系统发送',
  `effective_time` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT ' 生效时间',
  `expiry_time` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `scope` tinyint(4) DEFAULT NULL COMMENT '使用范围 ： 0-所有商品， 1-指定商品 2-指定店铺',
  `notes` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `update_on` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新时间',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
  `create_on` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建时间',
  `create_by` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='优惠券';

/*Data for the table `e_coupon` */

/*Table structure for table `f_account` */

DROP TABLE IF EXISTS `f_account`;

CREATE TABLE `f_account` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `customer_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户编号',
  `type` char(3) COLLATE utf8mb4_general_ci NOT NULL COMMENT '账户类型',
  `balance` decimal(10,2) NOT NULL COMMENT '余额',
  `available` decimal(10,2) NOT NULL COMMENT '可用额',
  `frozen` decimal(10,2) NOT NULL COMMENT '冻结额',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账户';

/*Data for the table `f_account` */

/*Table structure for table `f_account_log` */

DROP TABLE IF EXISTS `f_account_log`;

CREATE TABLE `f_account_log` (
  `id` bigint(20) NOT NULL,
  `customer_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户编码',
  `account_type` char(3) COLLATE utf8mb4_general_ci NOT NULL,
  `balance` decimal(10,2) NOT NULL,
  `amount` decimal(10,2) NOT NULL COMMENT '金额:增加的为正数, 减少的为负',
  `occur_on` timestamp NOT NULL COMMENT '变更时间',
  `change_mode` char(3) COLLATE utf8mb4_general_ci NOT NULL COMMENT '变更方式 1，exp 提现 2，trans 转账 3，back 退账 4，order 订单 5，bonus 分红',
  `bill_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '单号, 对应的单号',
  `notes` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注说明',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='账户流水';

/*Data for the table `f_account_log` */

/*Table structure for table `f_adjuestment` */

DROP TABLE IF EXISTS `f_adjuestment`;

CREATE TABLE `f_adjuestment` (
  `no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '调整单号',
  `customer_no` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '客户编码',
  `account_type` char(3) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账户类型',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '转出金额',
  `rate` tinyint(4) DEFAULT NULL COMMENT '汇率',
  `fee` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手续费',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型 1，转赠 2，退回',
  `notes` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '说明',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `update_on` timestamp NOT NULL COMMENT '更新时间',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `create_on` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='调整';

/*Data for the table `f_adjuestment` */

/*Table structure for table `f_charge` */

DROP TABLE IF EXISTS `f_charge`;

CREATE TABLE `f_charge` (
  `no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '充值单号',
  `customer_no` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '客户编码',
  `account_type` char(3) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '账户类型',
  `amount` decimal(10,2) DEFAULT NULL COMMENT '转出金额',
  `payment_no` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '支付流水号 当支付成功的才写入',
  `payment_method` char(3) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'ALI 支付宝 WXP 微信 CAH 现金',
  `payment_amount` decimal(10,2) DEFAULT NULL COMMENT '转入金额',
  `rate` tinyint(4) DEFAULT NULL COMMENT '汇率',
  `fee` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手续费',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型 1，转赠 2，退回',
  `notes` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '说明',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `update_on` timestamp NOT NULL COMMENT '更新时间',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `create_on` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='充值';

/*Data for the table `f_charge` */

/*Table structure for table `f_coupons` */

DROP TABLE IF EXISTS `f_coupons`;

CREATE TABLE `f_coupons` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `customer_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '客户编号',
  `coupon_id` bigint(20) NOT NULL COMMENT '账户类型',
  `coupon_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '余额',
  `take_time` timestamp NOT NULL COMMENT '领取时间',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `use_time` timestamp NULL DEFAULT NULL COMMENT '使用时间',
  `sale_no` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '销售单号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='我的优惠券';

/*Data for the table `f_coupons` */

/*Table structure for table `f_invoice` */

DROP TABLE IF EXISTS `f_invoice`;

CREATE TABLE `f_invoice` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `order_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '订单号',
  `no` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发票编号',
  `type` tinyint(4) DEFAULT NULL COMMENT '发票类型',
  `titile` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发票抬头',
  `content` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '发票内容',
  `mobile` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `create_on` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `update_on` timestamp NOT NULL COMMENT '更新时间',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `f_invoice` */

/*Table structure for table `f_payment_log` */

DROP TABLE IF EXISTS `f_payment_log`;

CREATE TABLE `f_payment_log` (
  `payment_no` varchar(20) COLLATE utf8mb4_general_ci NOT NULL,
  `type` tinyint(4) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `fee` decimal(10,2) DEFAULT NULL,
  `sale_no` varchar(20) COLLATE utf8mb4_general_ci NOT NULL COMMENT '销售单号',
  `status` tinyint(4) NOT NULL,
  `req_on` timestamp NOT NULL,
  `req_text` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `resp_on` timestamp NULL DEFAULT NULL,
  `resp_trade_no` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `resp_text` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`payment_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `f_payment_log` */

/*Table structure for table `f_transfer` */

DROP TABLE IF EXISTS `f_transfer`;

CREATE TABLE `f_transfer` (
  `no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '转账号',
  `src_customer_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '转出客户编码',
  `src_account_type` char(3) COLLATE utf8mb4_general_ci NOT NULL COMMENT '转出账户类型',
  `dest_customer_no` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '转入客户编码',
  `dest_account_type` char(3) COLLATE utf8mb4_general_ci NOT NULL COMMENT '转入账户类型',
  `src_amount` decimal(10,2) NOT NULL COMMENT '转出金额',
  `dest_amount` decimal(10,2) NOT NULL COMMENT '转入金额',
  `rate` tinyint(4) NOT NULL COMMENT '汇率',
  `fee` decimal(10,2) NOT NULL COMMENT '手续费',
  `type` tinyint(4) NOT NULL COMMENT '类型 1，转赠 2，退回',
  `notes` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '说明',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `update_on` timestamp NOT NULL COMMENT '更新时间',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `create_on` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  PRIMARY KEY (`no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='转账';

/*Data for the table `f_transfer` */

/*Table structure for table `p_brand` */

DROP TABLE IF EXISTS `p_brand`;

CREATE TABLE `p_brand` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cn_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文名称',
  `en_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '英文名称',
  `logo` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'LOGO图标',
  `website` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站',
  `stroy` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '品牌故事',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `p_brand` */

/*Table structure for table `p_catalog` */

DROP TABLE IF EXISTS `p_catalog`;

CREATE TABLE `p_catalog` (
  `id` bigint(20) NOT NULL,
  `parent_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `thumb` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `idx` tinyint(4) DEFAULT NULL,
  `keywords` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `notes` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `p_catalog` */

/*Table structure for table `p_catalog_attr` */

DROP TABLE IF EXISTS `p_catalog_attr`;

CREATE TABLE `p_catalog_attr` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `catalog_id` bigint(20) NOT NULL COMMENT '分类ID',
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性名称',
  `values` varchar(250) COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性值项，用逗号隔里',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `p_catalog_attr` */

/*Table structure for table `p_goods` */

DROP TABLE IF EXISTS `p_goods`;

CREATE TABLE `p_goods` (
  `id` bigint(20) NOT NULL,
  `catalog_id` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `code` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品编码',
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品名称',
  `bar_code` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '商品条码',
  `brand_id` bigint(20) DEFAULT NULL,
  `thumb` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '缩略图',
  `unit` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '单位',
  `spec` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `idx` tinyint(4) DEFAULT NULL COMMENT '排序',
  `sale_price` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '销售价',
  `market_price` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '市场价',
  `pv` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '价值 ',
  `status` tinyint(4) DEFAULT NULL COMMENT '销售状态',
  `average_cost` decimal(10,2) DEFAULT NULL COMMENT '商品加权平均成本',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `p_goods` */

/*Table structure for table `p_goods_attr` */

DROP TABLE IF EXISTS `p_goods_attr`;

CREATE TABLE `p_goods_attr` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `goods_id` bigint(20) NOT NULL,
  `name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性名称',
  `values` varchar(250) COLLATE utf8mb4_general_ci NOT NULL COMMENT '属性值项，用逗号隔里',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `p_goods_attr` */

/*Table structure for table `p_sku` */

DROP TABLE IF EXISTS `p_sku`;

CREATE TABLE `p_sku` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `goods_id` bigint(20) NOT NULL COMMENT '商品ID',
  `attr_set` varchar(500) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '属性集',
  `sale_price` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '销售价',
  `status` tinyint(4) DEFAULT NULL COMMENT '销售状态 0',
  `thumb` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '缩略图',
  `pv` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '价值',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `p_sku` */

/*Table structure for table `p_tag` */

DROP TABLE IF EXISTS `p_tag`;

CREATE TABLE `p_tag` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `cn_name` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '中文名称',
  `en_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '英文名称',
  `logo` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'LOGO图标',
  `website` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '网站',
  `stroy` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '品牌故事',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='标签';

/*Data for the table `p_tag` */

/*Table structure for table `q_stock` */

DROP TABLE IF EXISTS `q_stock`;

CREATE TABLE `q_stock` (
  `id` bigint(20) NOT NULL,
  `goods_id` bigint(20) NOT NULL,
  `sku_id` bigint(20) NOT NULL,
  `turnover` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '周转数量',
  `lock_qty` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '锁定数量',
  `transit_qty` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '在途数量',
  `available_qty` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '可用数量'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='库存';

/*Data for the table `q_stock` */

/*Table structure for table `y_advert` */

DROP TABLE IF EXISTS `y_advert`;

CREATE TABLE `y_advert` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `pos_code` char(3) COLLATE utf8mb4_general_ci NOT NULL COMMENT '位置编码',
  `tag_id` varchar(10) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '标签',
  `title` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '标题',
  `images` varchar(250) COLLATE utf8mb4_general_ci NOT NULL COMMENT '缩略图',
  `idx` tinyint(4) DEFAULT NULL COMMENT '排序',
  `link_url` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接',
  `create_on` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `update_on` timestamp NOT NULL,
  `update_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='广告';

/*Data for the table `y_advert` */

/*Table structure for table `y_express_template_limit` */

DROP TABLE IF EXISTS `y_express_template_limit`;

CREATE TABLE `y_express_template_limit` (
  `id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `templ_id` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `AREAS指定地区` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `FIRST_COST首件运费` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ADDITION_COST加件运费` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `FIRST_COUNT首件标准` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `ADDITION_COUNT加件标准` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `FREE_VALUATION0:按件;1:按重量;2:按体积` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `FREE_STANDARD免运费标准` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `y_express_template_limit` */

/*Table structure for table `y_login_log` */

DROP TABLE IF EXISTS `y_login_log`;

CREATE TABLE `y_login_log` (
  `id` bigint(20) NOT NULL,
  `user_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL,
  `client` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '客户端',
  `client_version` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '客户端版本号',
  `client_device` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录时设备号',
  `command` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作类型',
  `login_mode` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '第三方/邮箱/手机等',
  `login_time` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录时间',
  `login_ipaddr` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '登录IP地址',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='登录日志';

/*Data for the table `y_login_log` */

/*Table structure for table `z_module` */

DROP TABLE IF EXISTS `z_module`;

CREATE TABLE `z_module` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '父模块ID',
  `type` char(3) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '类型',
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `code` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编码（唯一的，java类名）',
  `operate` tinyint(4) DEFAULT NULL COMMENT '操作',
  `icon` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '图标',
  `url` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '链接地址',
  `idx` tinyint(4) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `update_on` timestamp NOT NULL COMMENT '更新时间',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `create_on` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='模块';

/*Data for the table `z_module` */

insert  into `z_module`(`id`,`parent_id`,`type`,`name`,`code`,`operate`,`icon`,`url`,`idx`,`status`,`update_on`,`update_by`,`create_on`,`create_by`) values 
(29,28,'M','待支付订单','ORDERS_UNPAY',63,'','',0,0,'2021-05-21 14:36:16','AutoModifyer','2021-05-21 14:36:16','AutoModifyer'),
(30,28,'M','已支付订单','ORDERS_PAYED',63,'','',0,0,'2021-05-21 14:38:03','AutoModifyer','2021-05-21 14:38:03','AutoModifyer'),
(28,0,'M','订单管理','ORDERS_MANAGEMENT',63,'','',0,0,'2021-05-21 14:35:09','AutoModifyer','2021-05-21 14:35:09','AutoModifyer'),
(27,24,'M','分类管理','GOODS_CALOG',63,'','',0,0,'2021-05-21 11:35:48','AutoModifyer','2021-05-21 11:35:48','AutoModifyer'),
(26,22,'M','用户管理','SYSTEM_USER',63,'','/userList',0,0,'2021-06-03 15:32:16','AutoModifyer','2021-05-21 11:33:25','AutoModifyer'),
(24,0,'M','商品管理','GOODS_MANAGEMENT',32,'','',0,0,'2021-05-21 11:34:10','AutoModifyer','2021-05-14 17:55:37','AutoModifyer'),
(25,22,'M','角色管理','SYSTEM_ROLE',63,'','/roleList',0,0,'2021-06-03 15:32:02','AutoModifyer','2021-05-21 11:29:42','AutoModifyer'),
(22,0,'M','系统管理','SYSTEM_MANAGEMENT',1,'https://wwww.g-zz.com','',2,0,'2021-06-03 15:32:31','AutoModifyer','2020-10-15 00:59:50','ricky'),
(23,22,'M','模块管理','SYSTEM_MODULE',63,'https://wwww.g-zz.com','/moduleList',0,0,'2021-06-03 15:31:46','AutoModifyer','2020-10-15 01:02:40','ricky');

/*Table structure for table `z_role` */

DROP TABLE IF EXISTS `z_role`;

CREATE TABLE `z_role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `code` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '编码（唯一的，java类名）',
  `idx` tinyint(4) DEFAULT NULL COMMENT '排序',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `update_on` timestamp NOT NULL COMMENT '更新时间',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `create_on` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  `notes` varchar(200) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色';

/*Data for the table `z_role` */

insert  into `z_role`(`id`,`name`,`code`,`idx`,`status`,`update_on`,`update_by`,`create_on`,`create_by`,`notes`) values 
(10,'管理员','ADMIN',3,0,'2021-06-08 10:44:13','AutoModifyer','2021-06-08 10:44:13','AutoModifyer','管理员'),
(9,'操作人','OPERTACTOR',3,0,'2021-06-08 11:07:18','AutoModifyer','2021-06-08 11:07:18','AutoModifyer','操作人');

/*Table structure for table `z_role_permission` */

DROP TABLE IF EXISTS `z_role_permission`;

CREATE TABLE `z_role_permission` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `module_id` bigint(20) NOT NULL COMMENT '模块ID',
  `has_power` tinyint(4) NOT NULL COMMENT '拥有权限',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='角色权限';

/*Data for the table `z_role_permission` */

insert  into `z_role_permission`(`id`,`role_id`,`module_id`,`has_power`) values 
(67,10,22,1),
(68,10,25,63),
(69,10,26,63),
(70,10,27,6),
(71,10,28,63),
(72,10,29,63),
(73,10,30,63),
(74,9,24,32),
(75,9,27,63);

/*Table structure for table `z_user` */

DROP TABLE IF EXISTS `z_user`;

CREATE TABLE `z_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `user_id` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '用户名',
  `passwd` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '密码盐',
  `pet_name` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '名称',
  `email` varchar(100) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '手机号',
  `allow_ipaddr` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '允许登录IP',
  `deny_ipaddr` varchar(250) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '拒绝登录IP',
  `notes` varchar(50) COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '备注',
  `status` tinyint(4) NOT NULL COMMENT '状态',
  `update_on` timestamp NOT NULL COMMENT '更新时间',
  `update_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '更新人',
  `create_on` timestamp NOT NULL COMMENT '创建时间',
  `create_by` varchar(50) COLLATE utf8mb4_general_ci NOT NULL COMMENT '创建人',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户';

/*Data for the table `z_user` */

insert  into `z_user`(`id`,`user_id`,`passwd`,`salt`,`pet_name`,`email`,`mobile`,`allow_ipaddr`,`deny_ipaddr`,`notes`,`status`,`update_on`,`update_by`,`create_on`,`create_by`) values 
(1,'23','vsZBxWqcczcpEpikTUAdMxHZhyoAiEnW','I3UwZJuGuDoIoL8m','23',NULL,NULL,'','',NULL,0,'2021-06-21 11:50:40','AutoModifyer','2021-06-21 11:50:40','AutoModifyer'),
(5,'sdfsdf','bJUuxUhNNSCbxifCewERePjHLNiudgDb','x8SNN7N2HV7MB07a','sdf',NULL,NULL,'asfd','asdfasdf',NULL,0,'2021-06-23 15:37:44','AutoModifyer','2021-06-23 15:37:44','AutoModifyer'),
(4,'userId','password','salt','petName',NULL,NULL,NULL,NULL,NULL,2,'2021-06-22 14:45:51','testing','2021-06-22 14:45:51','testing'),
(6,'eqwer','PcjaqraAdXWaqpTNbmleTyBOHOOxFFdD','G3UhzqrQcNqb5p53','sdfa',NULL,NULL,'3323','23',NULL,0,'2021-06-23 15:47:27','AutoModifyer','2021-06-23 15:47:27','AutoModifyer'),
(7,'332323','jKztiQuLPNlKhTRfzIFMmpMzpMVFuKUd','ioyRvNvwbwyARTQN','232323',NULL,NULL,'2323','2323',NULL,0,'2021-06-23 16:13:12','AutoModifyer','2021-06-23 16:13:12','AutoModifyer'),
(8,'2312','JOusENQhJPvGPfsjBphkupFznhPZqkfi','ZqOWZY7NVV1yMXl0','12',NULL,NULL,'3323','23',NULL,0,'2021-06-23 16:16:13','AutoModifyer','2021-06-23 16:16:13','AutoModifyer'),
(9,'3423412','pDUEYXwxXAxDNKeJzaLDmrsjukyAkqMm','VFjQrGdCiZ6uMAsv','323',NULL,NULL,'33','23',NULL,0,'2021-06-23 16:18:39','AutoModifyer','2021-06-23 16:18:39','AutoModifyer'),
(10,'23452345','DuTLAqzRezmhWDRYGWWtmqOVPLwujQoh','Z2NC7Z0WggjtVNP0','3454',NULL,NULL,'52345','34523',NULL,0,'2021-06-23 16:18:55','AutoModifyer','2021-06-23 16:18:55','AutoModifyer'),
(11,'2323','zvAZGZPpXVgVcNfPazwhYKTYqDXNqRYf','oJMmOwtNpHQ7RM1g','23',NULL,NULL,'','234',NULL,0,'2021-06-23 16:19:51','AutoModifyer','2021-06-23 16:19:51','AutoModifyer'),
(12,'65456456','tSfeqMewkKMQofxbXKfEAgBpoxGQEhhp','wcEdq2jkwXBNVSkG','456456',NULL,NULL,'','',NULL,0,'2021-06-23 16:20:03','AutoModifyer','2021-06-23 16:20:03','AutoModifyer'),
(13,'45645','cuiuyZZbseFwqKEEHsYGbPXAwbWIrnTP','sHlvcEj3PVQK3tsR','4565',NULL,NULL,'','',NULL,0,'2021-06-23 16:20:16','AutoModifyer','2021-06-23 16:20:16','AutoModifyer'),
(14,'4564562','dayuxmTSDKwOMRligNFHCpWJigdwxIse','4JAldFjXE3hLMwER','454',NULL,NULL,'23','23',NULL,0,'2021-06-23 16:20:31','AutoModifyer','2021-06-23 16:20:31','AutoModifyer'),
(15,'87867867','RDwPqurpdzULcUDdzjdPHlbqYqbTXkrk','grX86nuuELK7fwbY','678678',NULL,NULL,'','',NULL,0,'2021-06-23 16:20:50','AutoModifyer','2021-06-23 16:20:50','AutoModifyer'),
(16,'578','EvwfJuEmMQIhDBgkxTzaoecACkDyFATk','kjkIZ1AfBc9RNhrM','785',NULL,NULL,'','',NULL,0,'2021-06-23 16:21:06','AutoModifyer','2021-06-23 16:21:06','AutoModifyer'),
(17,'6785678','VefXBygHotrQErwCsuDInaZokMSUBWvb','UURwKczBWVWpyMMT','578',NULL,NULL,'','',NULL,0,'2021-06-23 16:21:19','AutoModifyer','2021-06-23 16:21:19','AutoModifyer'),
(18,'78fghd','bnRFlOlYpwhwEPEjohUMyuVZzaWgEjPE','pbrNHJM46zb7AtIJ','dfgh',NULL,NULL,'','',NULL,0,'2021-06-23 16:21:35','AutoModifyer','2021-06-23 16:21:35','AutoModifyer'),
(19,'45656','YJQGCAzBjJLPxOSILNwHuAngDPMJaAnb','oBwRPFoSF2OzblV3','546346',NULL,NULL,'','',NULL,0,'2021-06-23 16:21:47','AutoModifyer','2021-06-23 16:21:47','AutoModifyer'),
(20,'3456','ZvQhkoDmdOtIMQZHkdgYfRsGVQDqfwOs','sgG6NkxEYN721AvR','4565',NULL,NULL,'','',NULL,0,'2021-06-23 16:21:59','AutoModifyer','2021-06-23 16:21:59','AutoModifyer'),
(21,'456','OTskaKjwQXYQxFLYklBWccNPgabYJjGd','KyWoAhncchZ5F0B6','thhdgh56',NULL,NULL,'','',NULL,0,'2021-06-23 16:22:11','AutoModifyer','2021-06-23 16:22:11','AutoModifyer'),
(22,'hjfghh','ovZaUnunPOGUESzyfQKuYBsoQfjQEbrx','m4MWdLsHvMdIFNFE','ghj',NULL,NULL,'','',NULL,0,'2021-06-23 16:22:23','AutoModifyer','2021-06-23 16:22:23','AutoModifyer'),
(23,'ghjfgh','PRKfcpXhROdCpPiZOvveRMZbZOAiWgxH','A7MKWztXirncyeAP','fghghjfgh',NULL,NULL,'','',NULL,0,'2021-06-23 16:22:36','AutoModifyer','2021-06-23 16:22:36','AutoModifyer'),
(24,'2222222','TPFtXVPjPmdwFuHovfyXxYtFdIAIXWwo','UCqG2mtj8WADSGfB','34563',NULL,NULL,'','',NULL,0,'2021-06-23 16:23:26','AutoModifyer','2021-06-23 16:23:26','AutoModifyer');

/*Table structure for table `z_user_roles` */

DROP TABLE IF EXISTS `z_user_roles`;

CREATE TABLE `z_user_roles` (
  `id` bigint(20) NOT NULL COMMENT 'ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `user_id` bigint(20) NOT NULL COMMENT '模块ID',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci COMMENT='用户角色';

/*Data for the table `z_user_roles` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
