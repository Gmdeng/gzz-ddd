package com.gzz.retail.application.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.system.command.ModuleAuditCmd;
import com.gzz.retail.application.system.command.ModuleDeleteCmd;
import com.gzz.retail.application.system.command.ModuleInsertCmd;
import com.gzz.retail.application.system.command.ModuleUpdateCmd;
import com.gzz.retail.domain.system.ModuleFactory;
import com.gzz.retail.domain.system.entity.Module;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模块管理Command
 *
 */
@Slf4j
@Service
public class ModuleCmdApplication {
    @Autowired
    private IZModuleMapper moduleMapper;

    @Autowired
    private ModuleFactory moduleFactory;

    /**
     *
     * @param cmd
     */
    public void insertCmd(ModuleInsertCmd cmd){
        ZModule zModule = BeanConvertUtil.convertOne(ModuleInsertCmd.class, ZModule.class, cmd, (src, dest) -> {
            log.info("=========================" + src);
        });
        moduleMapper.insert(zModule);

    }

    /**
     *
     * @param cmd
     */
    public void updateCmd(ModuleUpdateCmd cmd){
        ZModule zModule = BeanConvertUtil.convertOne(ModuleUpdateCmd.class, ZModule.class, cmd);
        moduleMapper.update(zModule);
    }

    public void deleteCmd(ModuleDeleteCmd cmd){
        Module module = moduleFactory.buildModule(cmd.getModuleId());
        module.delete();
    }

    public void auditCmd(ModuleAuditCmd cmd){
        Module module = moduleFactory.buildModule(cmd.getModuleId());
        if(cmd.getStatus() == 0){
            module.accept();
        }else {
            module.reject();
        }
    }
}
