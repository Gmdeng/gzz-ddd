package com.gzz.retail.domain.goods.repo;

import com.gzz.retail.domain.goods.entity.Attribute;
import com.gzz.retail.infra.persistence.mapper.IPAttributeMapper;
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

    }
}
