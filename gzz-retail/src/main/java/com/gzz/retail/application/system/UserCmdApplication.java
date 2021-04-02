package com.gzz.retail.application.system;

import com.gzz.core.exception.BizzException;
import com.gzz.core.util.StringUtil;
import com.gzz.core.validation.ValidationUtils;
import com.gzz.retail.application.ICommand;
import com.gzz.retail.application.system.command.UserDeleteCmd;
import com.gzz.retail.application.system.command.UserInsertCmd;
import com.gzz.retail.application.system.command.UserModifyPasswdCmd;
import com.gzz.retail.application.system.command.UserUpdateCmd;
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
    public void insertCmd(UserInsertCmd cmd){

    }

    /**
     * 修改
     * @param cmd
     */
    public void updateCmd(UserUpdateCmd cmd){

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
