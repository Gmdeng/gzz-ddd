package com.gzz.retail.application.cqrs.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.system.command.RoleAuditCmd;
import com.gzz.retail.application.cqrs.system.command.RoleDeleteCmd;
import com.gzz.retail.application.cqrs.system.command.RoleSaveCmd;
import com.gzz.retail.domain.system.entity.Permission;
import com.gzz.retail.domain.system.entity.Role;
import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.domain.system.repo.RoleRepo;
import com.gzz.retail.infra.defines.types.OperateType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
            List<Permission> perms = BeanConvertUtil.convertList(src.getPrivileges(), Permission.class,(s, d)->{
                //d.setRoleId(dest.getRoleId().getId());
                //Set<OperateType> operates = Arrays.stream(s.getValues()).mapToObj(OperateType::valueOf).collect(Collectors.toSet());
                Set<OperateType> operates = Arrays.stream(s.getValues()).mapToObj(it-> { return OperateType.valueOf(it).get();}).collect(Collectors.toSet());
                d.setHasOperate(operates);
            } );
            dest.setPermissions(perms);
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
