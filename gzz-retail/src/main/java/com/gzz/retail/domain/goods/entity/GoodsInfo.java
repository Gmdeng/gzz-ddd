package com.gzz.retail.domain.goods.entity;

import lombok.Data;

@Data
public class GoodsInfo {
    // ID
    private Long id;
    // 商品ID
    private Long goodsId;
    // 图片
    private String images;
    // 视频地址
    private String voideoUrl;
    // 介绍内容
    private String contents;

}
