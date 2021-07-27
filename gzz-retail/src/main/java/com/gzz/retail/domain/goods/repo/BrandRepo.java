package com.gzz.retail.domain.goods.repo;

import com.gzz.core.exception.BizzException;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.domain.goods.entity.Brand;
import com.gzz.retail.domain.system.entity.User;
import com.gzz.retail.infra.defines.CommStatus;
import com.gzz.retail.infra.persistence.mapper.IPBrandMapper;
import com.gzz.retail.infra.persistence.pojo.PBrandPo;
import com.gzz.retail.infra.persistence.pojo.ZUserPo;
import com.gzz.retail.infra.persistence.pojo.ZUserRolePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class BrandRepo {
    @Autowired
    private IPBrandMapper mapper;

    /**
     * 保存数据
     */
    @Transactional
    public void save(Brand entity){
        PBrandPo po = BeanConvertUtil.convertOne(entity, PBrandPo.class);
        int num =0;
        if(po.getId()== null) {
            num = mapper.insert(po);
        }else{
            num = mapper.update(po);
        }
        if(num ==0)
            throw new BizzException("保存数据异常");
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delete(Brand entity){
        mapper.delete(entity.getId());
    }
}
