package com.gzz.retail.application.cqrs.goods;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.goods.dto.BrandDto;
import com.gzz.retail.application.cqrs.goods.dto.BrandFormDto;
import com.gzz.retail.application.cqrs.goods.queries.BrandQuery;
import com.gzz.retail.domain.system.primitive.RoleName;
import com.gzz.retail.infra.persistence.mapper.IPBrandMapper;
import com.gzz.retail.infra.persistence.pojo.PBrandPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BrandQueryApplication {
    @Autowired
    private IPBrandMapper brandMapper;

    /**
     * 获取表单数据
     * @param brandId
     * @return
     */
    public BrandFormDto getFormById(Long brandId){
        PBrandPo po = brandMapper.getById(brandId);
        BrandFormDto dto = BeanConvertUtil.convertOne(po, BrandFormDto.class);
        return dto;
    }

    /**
     * 获取详细
     * @param brandId
     * @return
     */
    public BrandDto getDetailById(Long brandId){
        PBrandPo po = brandMapper.getById(brandId);
        BrandDto dto = BeanConvertUtil.convertOne(po, BrandDto.class);
        List<RoleName> roles = null;

        return dto;
    }
    /**
     * 获取分页面
     * @param query
     * @return
     */
    public List<BrandDto> getBrandsByPage(BrandQuery query){
        List<PBrandPo> dataList = brandMapper.findListByPage(query.toParam(), query.getPager());
        List<BrandDto> list = BeanConvertUtil.convertList( dataList, BrandDto.class, (src, dest) ->{
            // dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return list;
    }
}
