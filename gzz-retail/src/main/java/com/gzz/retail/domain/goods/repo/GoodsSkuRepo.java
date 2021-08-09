package com.gzz.retail.domain.goods.repo;

import com.gzz.core.util.BeanUtil;
import com.gzz.retail.domain.goods.entity.GoodsSku;
import com.gzz.retail.domain.goods.entity.GoodsSpu;
import com.gzz.retail.infra.persistence.mapper.IPGoodsSkuMapper;
import com.gzz.retail.infra.persistence.mapper.IPGoodsSpuMapper;
import com.gzz.retail.infra.persistence.pojo.PGoodsSkuPo;
import com.gzz.retail.infra.persistence.pojo.PGoodsSpuPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
public class GoodsSkuRepo {

    @Autowired
    private IPGoodsSkuMapper mapper;

    /**
     * 加载商品
     *
     * @return
     */
    public GoodsSku loadGoods(Long goodsId) {
        PGoodsSkuPo pGoods = mapper.getById(goodsId);
        if (pGoods == null) return null;
        return BeanUtil.shadowCopy(pGoods, GoodsSku.class);
    }


    /**
     * 保存商品
     */
    @Transactional
    public void save(GoodsSku spu) {
        PGoodsSkuPo pGoods = BeanUtil.deepCopy(spu, PGoodsSkuPo.class);
        if (pGoods.getId() == null) {
            mapper.insert(pGoods);
        } else {
            mapper.update(pGoods);
        }

    }
}
