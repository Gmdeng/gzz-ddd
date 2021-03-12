package com.gzz.retail.domain.goods;

import com.gzz.core.util.BeanUtil;
import com.gzz.retail.domain.goods.model.Goods;
import com.gzz.retail.infra.persistence.mapper.IPGoodsMapper;
import com.gzz.retail.infra.persistence.pojo.PGoods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品仓库
 */
@Slf4j
@Component
public class GoodsRepo {

    @Autowired
    private IPGoodsMapper pGoodsMapper;

    /**
     * 加载商品
     * @return
     */
    public Goods loadGoods(Long goodsId){
        PGoods pGoods = pGoodsMapper.getById(goodsId);
        if(pGoods == null) return null;
        return  BeanUtil.shadowCopy(pGoods, Goods.class);
    }


    /**
     * 保存商品
     */
    @Transactional
    public void saveGoods(Goods goods){
      PGoods pGoods =  BeanUtil.deepCopy(goods, PGoods.class);
      if(pGoods.getId() == null){
          pGoodsMapper.insert(pGoods);
      }else{
          pGoodsMapper.update(pGoods);
      }

    }
}
