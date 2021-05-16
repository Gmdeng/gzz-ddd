package com.gzz.retail.application.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.system.command.*;
import com.gzz.retail.domain.system.entity.Module;
import com.gzz.retail.domain.system.entity.Role;
import com.gzz.retail.domain.system.primitive.ModuleId;
import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.domain.system.repo.RoleRepo;
import com.gzz.retail.infra.defines.types.OperateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;

/**
 * 角色管理Command
 *
 */
@Slf4j
@Service
public class RoleCmdApplication {
    @Autowired
    private RoleRepo roleRepo;


    /**
     *  执行保存命令
     * @param cmd
     */
    public void saveCmd(RoleSaveCmd cmd){
        Role role = BeanConvertUtil.convertOne(cmd, Role.class, (src, dest) -> {
            log.info("========================={}", src);
            if(src.getId() != null)
                dest.setRoleId(new RoleId(src.getId()));
        });
        roleRepo.save(role);
    }

    /**
     * 执行删除命令
     * @param cmd
     */
    public void deleteCmd(RoleDeleteCmd cmd){
//        Module module = moduleFactory.buildModule(cmd.getModuleId());
//        moduleRepo.delete(module);
    }

    /**
     * 执行审核命令
     * @param cmd
     */
    public void auditCmd(RoleAuditCmd cmd){
//        Module module = moduleFactory.buildModule(cmd.getModuleId());
//        if(cmd.getStatus() == 0){
//            module.approve();
//        }else {
//            module.reject();
//        }
    }
}
