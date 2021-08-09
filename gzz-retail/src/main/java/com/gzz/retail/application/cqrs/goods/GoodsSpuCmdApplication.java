package com.gzz.retail.application.cqrs.goods;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.goods.command.AttributeSaveCmd;
import com.gzz.retail.application.cqrs.goods.command.GoodsSpuSaveCmd;
import com.gzz.retail.domain.goods.entity.Attribute;
import com.gzz.retail.domain.goods.entity.GoodsSpu;
import com.gzz.retail.domain.goods.repo.AttributeRepo;
import com.gzz.retail.domain.goods.repo.GoodsSpuRepo;
import com.gzz.retail.infra.persistence.mapper.IPAttributeMapper;
import com.gzz.retail.infra.persistence.mapper.IPGoodsSpuMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class GoodsSpuCmdApplication {
    @Autowired
    private GoodsSpuRepo goodsSpuRepo;

    @Autowired
    private IPGoodsSpuMapper goodsSpuMapper;

    /*  *  执行保存命令
     * @param cmd
     */
    public void saveCmd(GoodsSpuSaveCmd cmd){
        GoodsSpu attribute = BeanConvertUtil.convertOne(cmd, GoodsSpu.class);
        goodsSpuRepo.save(attribute);
    }
}
