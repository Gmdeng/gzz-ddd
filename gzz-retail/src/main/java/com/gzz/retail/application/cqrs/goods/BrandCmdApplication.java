package com.gzz.retail.application.cqrs.goods;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.goods.command.BrandAuditCmd;
import com.gzz.retail.application.cqrs.goods.command.BrandDeleteCmd;
import com.gzz.retail.application.cqrs.goods.command.BrandSaveCmd;
import com.gzz.retail.application.cqrs.system.command.UserDeleteCmd;
import com.gzz.retail.application.cqrs.system.command.UserSaveCmd;
import com.gzz.retail.domain.goods.entity.Brand;
import com.gzz.retail.domain.goods.repo.BrandRepo;
import com.gzz.retail.domain.system.entity.User;
import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.domain.system.primitive.UserId;
import com.gzz.retail.domain.system.repo.UserRepo;
import com.gzz.retail.infra.persistence.mapper.IPBrandMapper;
import com.gzz.retail.infra.persistence.mapper.IZUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BrandCmdApplication {
    @Autowired
    private BrandRepo brandRepo;

    @Autowired
    private IPBrandMapper brandMapper;

    /*  *  执行保存命令
     * @param cmd
     */
    public void saveCmd(BrandSaveCmd cmd){
        Brand brand = BeanConvertUtil.convertOne(cmd, Brand.class);
        brandRepo.save(brand);
    }


    /**
     * 册除。。
     * @param cmd
     */
    public void deleteCmd(@Valid BrandDeleteCmd cmd){
        //ValidationUtils.validate(cmd);
//        User user = userRepo.loadUser(new UserId(cmd.getId()));
//        userRepo.delete(user);
    }

    /**
     *
     * @param cmd
     */
    public void auditCmd(BrandAuditCmd cmd) {

    }
}
