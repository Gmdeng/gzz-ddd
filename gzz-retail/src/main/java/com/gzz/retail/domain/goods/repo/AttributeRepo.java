package com.gzz.retail.domain.goods.repo;

import com.gzz.core.exception.BizzException;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.domain.goods.entity.Attribute;
import com.gzz.retail.infra.persistence.mapper.IPAttributeMapper;
import com.gzz.retail.infra.persistence.pojo.PAttributePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AttributeRepo {
    @Autowired
    private IPAttributeMapper mapper;
    /**
     * 保存数据
     */
    @Transactional
    public void save(Attribute entity){
        PAttributePo po = BeanConvertUtil.convertOne(entity, PAttributePo.class);
        int num =0;
        if(po.getId()== null) {
            num = mapper.insert(po);
        }else{
            num = mapper.update(po);
        }
        if(num ==0)
            throw new BizzException("保存数据异常");
    }
}
