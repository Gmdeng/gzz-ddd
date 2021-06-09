package com.gzz.retail.facade.api.admin.system;

import com.gzz.core.response.HttpResult;
import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.application.cqrs.system.UserCmdApplication;
import com.gzz.retail.application.cqrs.system.UserQueryApplication;
import com.gzz.retail.application.cqrs.system.command.RoleSaveCmd;
import com.gzz.retail.application.cqrs.system.command.UserSaveCmd;
import com.gzz.retail.application.cqrs.system.dto.RoleDto;
import com.gzz.retail.application.cqrs.system.dto.RoleFormDto;
import com.gzz.retail.application.cqrs.system.dto.UserDto;
import com.gzz.retail.application.cqrs.system.dto.UserFormDto;
import com.gzz.retail.application.cqrs.system.queries.RoleQuery;
import com.gzz.retail.application.cqrs.system.queries.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 系统角色管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/system/user")
public class UserApi {
    @Autowired
    private UserCmdApplication userCmdApp;
    @Autowired
    private UserQueryApplication userQueryApp;


    /**
     * 获取表单数据
     * @param id
     * @return
     */
    @GetMapping("/getData")
    public HttpResult getFormData(Long id){
        UserFormDto dto = userQueryApp.getUserFormById(id);
        return HttpResult.success(dto);
    }

    /**
     * 获取明细数据
     * @param id
     * @return
     */
    @GetMapping("/getDetail")
    public HttpResult getDetail(@Valid Long id){
        UserDto dto = userQueryApp.getUserDetailById(id);
        return HttpResult.success(dto);
    }

    /**
     * 分页数据列表
     *
     * @return
     */
    @GetMapping("/getDataListByPage")
    public HttpResult getDataListByPage(HttpServletRequest request) {
        ParamMap params = new ParamMap();
        Pager pager = new Pager(20);
        UserQuery query = new UserQuery(pager);
        List<UserDto> dataList = userQueryApp.getUsersByPage(query);

//        return HttpResult.success(toTreeNode(zeroNode, lists));
        return HttpResult.success().put("dataList", dataList).put("pager", pager);
    }

    /**
     * 保存数据
     *
     * @return
     */
    @PostMapping("/saveData")
    public HttpResult saveData(@Validated UserSaveCmd cmd) {
        log.info("接收到参数： {}",cmd.toString());
        userCmdApp.saveCmd(cmd);
        return HttpResult.success();
    }
}
