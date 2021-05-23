package com.gzz.retail.application.cqrs.system;

import com.gzz.core.exception.BizzException;
import com.gzz.core.validation.ValidationUtils;
import com.gzz.retail.application.ICommand;
import com.gzz.retail.application.cqrs.system.command.UserDeleteCmd;
import com.gzz.retail.application.cqrs.system.command.UserSaveCmd;
import com.gzz.retail.application.cqrs.system.command.UserModifyPasswdCmd;
import com.gzz.retail.infra.persistence.mapper.IZUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Executor
 */
@Service
public class UserCmdApplication {
    @Autowired
    private IZUserMapper izUserMapper;

    public void Execute(ICommand cmd){

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
       int i= izUserMapper.delete(cmd.getId());
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
