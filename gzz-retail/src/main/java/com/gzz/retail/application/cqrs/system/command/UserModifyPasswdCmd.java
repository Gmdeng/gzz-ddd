package com.gzz.retail.application.cqrs.system.command;

import lombok.Data;

/**
 *
 */
@Data
public class UserModifyPasswdCmd {
    private Long userId;

    private String oldPasswd;

    private String newPasswd;
}
