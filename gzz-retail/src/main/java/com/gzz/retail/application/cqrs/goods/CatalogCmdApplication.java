package com.gzz.retail.application.cqrs.goods;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.goods.command.CatalogAuditCmd;
import com.gzz.retail.application.cqrs.goods.command.CatalogDeleteCmd;
import com.gzz.retail.application.cqrs.goods.command.CatalogSaveCmd;
import com.gzz.retail.domain.goods.entity.Catalog;
import com.gzz.retail.domain.goods.repo.CatalogRepo;
import com.gzz.retail.infra.persistence.mapper.IPCatalogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 */
@Slf4j
@Service
public class CatalogCmdApplication {
    @Autowired
    private CatalogRepo catalogRepo;
    @Autowired
    private IPCatalogMapper catalogMapper;
    /**
     *  执行保存命令
     * @param cmd
     */
    public void saveCmd(CatalogSaveCmd cmd){
        Catalog catalog = BeanConvertUtil.convertOne(cmd, Catalog.class, (src, dest) -> {
            // log.info("========================={}", src);
            dest.setEnable(true);
        });
        catalogRepo.save(catalog);
    }

    /**
     * 执行删除命令
     * @param cmd
     */
    public void deleteCmd(CatalogDeleteCmd cmd){
        //Module module = moduleFactory.buildModule(cmd.getModuleId());
        catalogMapper.delete(cmd.getCatalogId());
    }

    /**
     * 执行审核命令
     * @param cmd
     */
    public void auditCmd(CatalogAuditCmd cmd){
//        Module module = moduleFactory.buildModule(cmd.getModuleId());
//        if(cmd.getStatus() == 0){
//            module.approve();
//        }else {
//            module.reject();
//        }
        //catalogMapper.(cmd.getCatalogId());
    }
}
