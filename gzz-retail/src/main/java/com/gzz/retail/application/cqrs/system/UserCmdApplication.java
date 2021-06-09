package com.gzz.retail.application.cqrs.system;

import com.gzz.core.exception.BizzException;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.core.validation.ValidationUtils;
import com.gzz.retail.application.ICommand;
import com.gzz.retail.application.cqrs.system.command.RoleSaveCmd;
import com.gzz.retail.application.cqrs.system.command.UserDeleteCmd;
import com.gzz.retail.application.cqrs.system.command.UserSaveCmd;
import com.gzz.retail.application.cqrs.system.command.UserModifyPasswdCmd;
import com.gzz.retail.domain.system.entity.Permission;
import com.gzz.retail.domain.system.entity.Role;
import com.gzz.retail.domain.system.entity.User;
import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.domain.system.repo.UserRepo;
import com.gzz.retail.infra.defines.types.OperateType;
import com.gzz.retail.infra.persistence.mapper.IZUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        });
        userRepo.save(user);
    }

    /**
     * 新增
     * @param cmd
     */
    public void insertCmd(UserSaveCmd cmd){

    }

    /**
     * 修改
     * @param cmd
     */
    public void updateCmd(UserSaveCmd cmd){

    }

    /**
     * 册除。。
     * @param cmd
     */
    public void deleteCmd(UserDeleteCmd cmd){
       ValidationUtils.validate(cmd);
       int i= userMapper.delete(cmd.getId());
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
