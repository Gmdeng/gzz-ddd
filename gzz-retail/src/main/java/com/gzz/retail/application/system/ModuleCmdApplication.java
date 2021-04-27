package com.gzz.retail.application.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.system.command.ModuleAuditCmd;
import com.gzz.retail.application.system.command.ModuleDeleteCmd;
import com.gzz.retail.application.system.command.ModuleSaveCmd;
import com.gzz.retail.domain.system.ModuleFactory;
import com.gzz.retail.domain.system.entity.ModuleDo;
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
        ModuleDo module = BeanConvertUtil.convertOne(ModuleSaveCmd.class, ModuleDo.class, cmd, (src, dest) -> {
            log.info("=========================" + src);
            if(src.getModuleId() != null)
                dest.setModuleId(new ModuleId(src.getModuleId()));
            dest.setParent(new ModuleDo(new ModuleId(src.getParentId())));
        });
        moduleRepo.save(module);
    }

    public void deleteCmd(ModuleDeleteCmd cmd){
        ModuleDo module = moduleFactory.buildModule(cmd.getModuleId());
        module.delete();
    }

    public void auditCmd(ModuleAuditCmd cmd){
        ModuleDo module = moduleFactory.buildModule(cmd.getModuleId());
        if(cmd.getStatus() == 0){
            module.accept();
        }else {
            module.reject();
        }
    }
}
