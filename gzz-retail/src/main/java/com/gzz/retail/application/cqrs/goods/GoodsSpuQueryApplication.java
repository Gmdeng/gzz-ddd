package com.gzz.retail.application.cqrs.goods;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.goods.dto.AttributeDto;
import com.gzz.retail.application.cqrs.goods.dto.AttributeFormDto;
import com.gzz.retail.application.cqrs.goods.dto.GoodsSpuDto;
import com.gzz.retail.application.cqrs.goods.dto.GoodsSpuFormDto;
import com.gzz.retail.application.cqrs.goods.queries.AttributeQuery;
import com.gzz.retail.application.cqrs.goods.queries.GoodsSpuQuery;
import com.gzz.retail.domain.goods.entity.AttributeOption;
import com.gzz.retail.infra.persistence.mapper.IPAttributeMapper;
import com.gzz.retail.infra.persistence.mapper.IPGoodsSpuMapper;
import com.gzz.retail.infra.persistence.pojo.PAttributeOptionPo;
import com.gzz.retail.infra.persistence.pojo.PAttributePo;
import com.gzz.retail.infra.persistence.pojo.PGoodsSpuPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GoodsSpuQueryApplication {
    @Autowired
    private IPGoodsSpuMapper goodsSpuMapper;

    /**
     * 获取表单数据
     * @param spuId
     * @returnuser
     */
    public GoodsSpuFormDto getFormById(Long spuId){
        PGoodsSpuPo po = goodsSpuMapper.getById(spuId);
        GoodsSpuFormDto dto = BeanConvertUtil.convertOne(po, GoodsSpuFormDto.class);

        return dto;
    }

    /**
     * 获取详细
     * @param spuId
     * @return
     */
    public GoodsSpuDto getDetailById(Long spuId){
        PGoodsSpuPo po = goodsSpuMapper.getById(spuId);
        GoodsSpuDto dto = BeanConvertUtil.convertOne(po, GoodsSpuDto.class);

        return dto;
    }
    /**
     * 获取分页面
     * @param query
     * @return
     */
    public List<GoodsSpuDto> getGoodsSpusByPage(GoodsSpuQuery query){
        List<PGoodsSpuPo> dataList = goodsSpuMapper.findListByPage(query.toParam(), query.getPager());
        List<GoodsSpuDto> list = BeanConvertUtil.convertList( dataList, GoodsSpuDto.class, (src, dest) ->{
            // dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return list;
    }
}
