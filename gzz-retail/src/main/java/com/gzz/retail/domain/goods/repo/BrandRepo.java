package com.gzz.retail.domain.goods.repo;

import com.gzz.retail.infra.persistence.mapper.IPBrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BrandRepo {
    @Autowired
    private IPBrandMapper brandMapper;
}
