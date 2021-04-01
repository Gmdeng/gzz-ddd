package com.gzz.retail.application.system.command;

import com.gzz.retail.application.ICommand;
import lombok.Data;

@Data
public class UserDeleteCmd implements ICommand {
    /**
     * ID id
     */
    private Long id;
    /**
     * 用户名 userId
     */
    private String userId;
}
