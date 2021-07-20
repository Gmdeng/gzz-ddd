package com.gzz.retail.domain.goods.repo;

import com.gzz.core.util.BeanUtil;
import com.gzz.retail.domain.goods.entity.GoodsSpu;
import com.gzz.retail.infra.persistence.mapper.IPGoodsSpuMapper;
import com.gzz.retail.infra.persistence.pojo.PGoodsSpuPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 商品仓库
 */
@Slf4j
@Component
public class GoodsSpuRepo {

    @Autowired
    private IPGoodsSpuMapper goodsSpuMapper;

    /**
     * 加载商品
     *
     * @return
     */
    public GoodsSpu loadGoods(Long goodsId) {
        PGoodsSpuPo pGoods = goodsSpuMapper.getById(goodsId);
        if (pGoods == null) return null;
        return BeanUtil.shadowCopy(pGoods, GoodsSpu.class);
    }


    /**
     * 保存商品
     */
    @Transactional
    public void saveGoodsSpu(GoodsSpu spu) {
        PGoodsSpuPo pGoods = BeanUtil.deepCopy(spu, PGoodsSpuPo.class);
        if (pGoods.getId() == null) {
            goodsSpuMapper.insert(pGoods);
        } else {
            goodsSpuMapper.update(pGoods);
        }

    }
}
