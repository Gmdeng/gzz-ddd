package com.gzz.retail.application.cqrs.goods;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.goods.dto.AttributeDto;
import com.gzz.retail.application.cqrs.goods.dto.AttributeFormDto;
import com.gzz.retail.application.cqrs.goods.queries.AttributeQuery;
import com.gzz.retail.domain.goods.entity.AttributeOption;
import com.gzz.retail.infra.persistence.mapper.IPAttributeMapper;
import com.gzz.retail.infra.persistence.pojo.PAttributeOptionPo;
import com.gzz.retail.infra.persistence.pojo.PAttributePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class AttributeQueryApplication {
    @Autowired
    private IPAttributeMapper attributeMapper;

    /**
     * 获取表单数据
     * @param attributeId
     * @returnuser
     */
    public AttributeFormDto getFormById(Long attributeId){
        PAttributePo po = attributeMapper.getById(attributeId);
        AttributeFormDto dto = BeanConvertUtil.convertOne(po, AttributeFormDto.class);
        List<PAttributeOptionPo> optinList = attributeMapper.findOptions(po.getId());
        List<AttributeOption> optins = BeanConvertUtil.convertList(optinList, AttributeOption.class);
        dto.setOptions(optins);
        return dto;
    }

    /**
     * 获取详细
     * @param attributeId
     * @return
     */
    public AttributeDto getDetailById(Long attributeId){
        PAttributePo po = attributeMapper.getById(attributeId);
        AttributeDto dto = BeanConvertUtil.convertOne(po, AttributeDto.class);
        List<PAttributeOptionPo> optinList = attributeMapper.findOptions(po.getId());
        List<AttributeOption> optins = BeanConvertUtil.convertList(optinList, AttributeOption.class);
        dto.setOptions(optins);
        return dto;
    }
    /**
     * 获取分页面
     * @param query
     * @return
     */
    public List<AttributeDto> getAttributesByPage(AttributeQuery query){
        List<PAttributePo> dataList = attributeMapper.findListByPage(query.toParam(), query.getPager());
        List<AttributeDto> list = BeanConvertUtil.convertList( dataList, AttributeDto.class, (src, dest) ->{
            // dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return list;
    }
}
