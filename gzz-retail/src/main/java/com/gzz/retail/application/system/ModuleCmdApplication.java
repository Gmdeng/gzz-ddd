package com.gzz.retail.application.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.system.command.ModuleAuditCmd;
import com.gzz.retail.application.system.command.ModuleDeleteCmd;
import com.gzz.retail.application.system.command.ModuleSaveCmd;
import com.gzz.retail.domain.system.ModuleFactory;
import com.gzz.retail.domain.system.entity.Module;
import com.gzz.retail.domain.system.primitive.ModuleId;
import com.gzz.retail.domain.system.repo.ModuleRepo;
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
    private ModuleRepo moduleRepo;

    @Autowired
    private ModuleFactory moduleFactory;

    /**
     *  执行保存命令
     * @param cmd
     */
    public void saveCmd(ModuleSaveCmd cmd){
        Module module = BeanConvertUtil.convertOne(ModuleSaveCmd.class, Module.class, cmd, (src, dest) -> {
            log.info("========================={}", src);
            if(src.getId() != null)
                dest.setModuleId(new ModuleId(src.getId()));
            dest.setParent(new Module(new ModuleId(src.getParentId())));
        });
        moduleRepo.save(module);
    }

    /**
     * 执行删除命令
     * @param cmd
     */
    public void deleteCmd(ModuleDeleteCmd cmd){
        Module module = moduleFactory.buildModule(cmd.getModuleId());
        moduleRepo.delete(module);
    }

    /**
     * 执行审核命令
     * @param cmd
     */
    public void auditCmd(ModuleAuditCmd cmd){
        Module module = moduleFactory.buildModule(cmd.getModuleId());
        if(cmd.getStatus() == 0){
            module.approve();
        }else {
            module.reject();
        }
    }
}
