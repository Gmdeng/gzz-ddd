package com.gzz.retail.application.cqrs.goods;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.goods.dto.GoodsSkuDto;
import com.gzz.retail.application.cqrs.goods.dto.GoodsSkuFormDto;
import com.gzz.retail.application.cqrs.goods.dto.GoodsSpuDto;
import com.gzz.retail.application.cqrs.goods.dto.GoodsSpuFormDto;
import com.gzz.retail.application.cqrs.goods.queries.GoodsSkuQuery;
import com.gzz.retail.application.cqrs.goods.queries.GoodsSpuQuery;
import com.gzz.retail.infra.persistence.mapper.IPGoodsSkuMapper;
import com.gzz.retail.infra.persistence.mapper.IPGoodsSpuMapper;
import com.gzz.retail.infra.persistence.pojo.PGoodsSkuPo;
import com.gzz.retail.infra.persistence.pojo.PGoodsSpuPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GoodsSkuQueryApplication {
    @Autowired
    private IPGoodsSkuMapper goodsSkuMapper;

    /**
     * 获取表单数据
     * @param skuId
     * @returnuser
     */
    public GoodsSkuFormDto getFormById(Long skuId){
        PGoodsSkuPo po = goodsSkuMapper.getById(skuId);
        GoodsSkuFormDto dto = BeanConvertUtil.convertOne(po, GoodsSkuFormDto.class);
        return dto;
    }

    /**
     * 获取详细
     * @param skuId
     * @return
     */
    public GoodsSkuDto getDetailById(Long skuId){
        PGoodsSkuPo po = goodsSkuMapper.getById(skuId);
        GoodsSkuDto dto = BeanConvertUtil.convertOne(po, GoodsSkuDto.class);
        return dto;
    }
    /**
     * 获取分页面
     * @param query
     * @return
     */
    public List<GoodsSkuDto> getGoodsSkusByPage(GoodsSkuQuery query){
        List<PGoodsSkuPo> dataList = goodsSkuMapper.findListByPage(query.toParam(), query.getPager());
        List<GoodsSkuDto> list = BeanConvertUtil.convertList( dataList, GoodsSkuDto.class, (src, dest) ->{
            // dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return list;
    }
}
