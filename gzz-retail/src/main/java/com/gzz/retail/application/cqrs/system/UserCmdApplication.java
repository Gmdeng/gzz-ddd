package com.gzz.retail.application.cqrs.system;

import com.gzz.core.exception.BizzException;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.core.validation.ValidationUtils;
import com.gzz.retail.application.ICommand;
import com.gzz.retail.application.cqrs.system.command.*;
import com.gzz.retail.domain.system.entity.Permission;
import com.gzz.retail.domain.system.entity.Role;
import com.gzz.retail.domain.system.entity.User;
import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.domain.system.primitive.UserId;
import com.gzz.retail.domain.system.repo.UserRepo;
import com.gzz.retail.infra.defines.types.OperateType;
import com.gzz.retail.infra.persistence.mapper.IZUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Executor
 */
@Slf4j
@Service
public class UserCmdApplication {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private IZUserMapper userMapper;

    /**
     *  执行保存命令
     * @param cmd
     */
    public void saveCmd(UserSaveCmd cmd){
        User user = BeanConvertUtil.convertOne(cmd, User.class, (src, dest) -> {
           dest.setUserId(new UserId(src.getId(), src.getUserId()));
           List<RoleId> roleIds =  Arrays.stream(src.getRoles()).map(id->{
                return new RoleId(Long.parseLong(id));
            }).collect(Collectors.toList());
           dest.setRoles(roleIds);
        });
        userRepo.save(user);
    }


    /**
     * 册除。。
     * @param cmd
     */
    public void deleteCmd(@Valid UserDeleteCmd cmd){
       //ValidationUtils.validate(cmd);
       User user = userRepo.loadUser(new UserId(cmd.getId()));
       userRepo.delete(user);
    }
    /**
     * 审批
     * @param cmd
     */
    public void approvalCmd(UserAuditCmd cmd){
        User user = userRepo.loadUser(new UserId(cmd.getId()));
        if(i ==0 ){
            throw new BizzException("该记录不存在。未能删除");
        }
    }

    /**
     * 更改密码
     * @param cmd
     */
    public void modifyPasswdCmd(UserModifyPasswdCmd cmd){}
}
