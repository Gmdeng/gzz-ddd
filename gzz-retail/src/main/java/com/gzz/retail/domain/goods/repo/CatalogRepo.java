package com.gzz.retail.domain.goods.repo;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.domain.goods.entity.Catalog;
import com.gzz.retail.infra.persistence.mapper.IPCatalogMapper;
import com.gzz.retail.infra.persistence.pojo.PCatalogPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CatalogRepo {
    @Autowired
    private IPCatalogMapper catalogMapper;
    /**
     * 保存数据
     */
    public void save(Catalog entity){
        PCatalogPo po = BeanConvertUtil.convertOne(entity, PCatalogPo.class);
        if(Objects.isNull(entity.getId())){
            catalogMapper.insert(po);
        }else{
            catalogMapper.update(po);
        }
    }
}
