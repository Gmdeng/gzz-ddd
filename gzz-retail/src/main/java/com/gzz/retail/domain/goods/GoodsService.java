package com.gzz.retail.domain.goods;

import com.gzz.retail.domain.goods.model.Brand;
import com.gzz.retail.infra.persistence.mapper.IPBrandMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 *
 */
@Component
public class GoodsService {
    @Autowired
    private IPBrandMapper pBrandMapper;

    public List<Brand> getBrandList() {
        Brand brand = new Brand();

        return pBrandMapper.findList(null);
    }
}
