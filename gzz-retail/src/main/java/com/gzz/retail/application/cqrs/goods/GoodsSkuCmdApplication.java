package com.gzz.retail.application.cqrs.goods;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.goods.command.GoodsSkuSaveCmd;
import com.gzz.retail.application.cqrs.goods.command.GoodsSpuSaveCmd;
import com.gzz.retail.domain.goods.entity.GoodsSku;
import com.gzz.retail.domain.goods.entity.GoodsSpu;
import com.gzz.retail.domain.goods.repo.GoodsSkuRepo;
import com.gzz.retail.domain.goods.repo.GoodsSpuRepo;
import com.gzz.retail.infra.persistence.mapper.IPGoodsSpuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoodsSkuCmdApplication {
    @Autowired
    private GoodsSkuRepo goodsSkuRepo;

    @Autowired
    private IPGoodsSpuMapper goodsSpuMapper;

    /*  *  执行保存命令
     * @param cmd
     */
    public void saveCmd(GoodsSkuSaveCmd cmd){
        GoodsSku attribute = BeanConvertUtil.convertOne(cmd, GoodsSku.class);
        goodsSkuRepo.save(attribute);
    }
}
