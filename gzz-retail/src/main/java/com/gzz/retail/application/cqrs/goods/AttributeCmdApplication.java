package com.gzz.retail.application.cqrs.goods;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.goods.command.*;
import com.gzz.retail.domain.goods.entity.Attribute;
import com.gzz.retail.domain.goods.entity.Brand;
import com.gzz.retail.domain.goods.repo.AttributeRepo;
import com.gzz.retail.domain.goods.repo.BrandRepo;
import com.gzz.retail.infra.persistence.mapper.IPAttributeMapper;
import com.gzz.retail.infra.persistence.mapper.IPBrandMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Slf4j
@Service
public class AttributeCmdApplication {
    @Autowired
    private AttributeRepo attributeRepo;

    @Autowired
    private IPAttributeMapper brandMapper;

    /*  *  执行保存命令
     * @param cmd
     */
    public void saveCmd(AttributeSaveCmd cmd){
        Attribute attribute = BeanConvertUtil.convertOne(cmd, Attribute.class);
        attributeRepo.save(attribute);
    }


//    /**
//     * 册除。。
//     * @param cmd
//     */
//    public void deleteCmd(@Valid AttributeDeleteCmd cmd){
//        //ValidationUtils.validate(cmd);
////        User user = userRepo.loadUser(new UserId(cmd.getId()));
////        userRepo.delete(user);
//    }
}
