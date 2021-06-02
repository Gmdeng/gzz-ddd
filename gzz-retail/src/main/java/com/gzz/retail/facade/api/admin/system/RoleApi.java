package com.gzz.retail.facade.api.admin.system;

import com.gzz.core.response.HttpResult;
import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.application.assembler.ModuleAssembler;
import com.gzz.retail.application.assembler.dto.ActionOption;
import com.gzz.retail.application.cqrs.system.RoleCmdApplication;
import com.gzz.retail.application.cqrs.system.RoleQueryApplication;
import com.gzz.retail.application.cqrs.system.command.*;
import com.gzz.retail.application.cqrs.system.dto.RoleDto;
import com.gzz.retail.application.cqrs.system.dto.RoleFormDto;
import com.gzz.retail.application.cqrs.system.queries.RoleQuery;
import com.gzz.retail.application.assembler.dto.MenuNode;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * 系统角色管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/system/role")
public class RoleApi {
    @Autowired
    private RoleCmdApplication roleCmdApp;
    @Autowired
    private RoleQueryApplication roleQueryApp;
    @Autowired
    private IZModuleMapper moduleMapper;


    /**
     * 获取所有动作
     * @return
     */
    @GetMapping("/getAllMenus")
    public HttpResult getAllActions(){
        List<ZModulePo> list = moduleMapper.findList(new ParamMap());
        MenuNode rootNode = new MenuNode();
        rootNode.setId(0L);
        List<MenuNode> menuNodeList = ModuleAssembler.toTreeMenus(list, rootNode);
        return HttpResult.success().data(menuNodeList);
    }

    /**
     * 获取表单数据
     * @param id
     * @return
     */
    @GetMapping("/getFormData")
    public HttpResult getFormData(Long id){
        RoleFormDto dto = roleQueryApp.getRoleFormById(id);
        return HttpResult.success(dto);
    }

    /**
     * 获取明细数据
     * @param id
     * @return
     */
    @GetMapping("/getDetail")
    public HttpResult getDetail(@Valid Long id){
        RoleDto dto = roleQueryApp.getRoleDetailById(id);
        return HttpResult.success(dto);
    }
    /**
     * 保存数据
     *
     * @return
     */
    @Validated
    @PostMapping("/saveData")
    public HttpResult saveData(@RequestBody RoleSaveCmd cmd) {
        log.info("接收到参数： {}",cmd.toString());
        // roleCmdApp.saveCmd(cmd);
        return HttpResult.success();
    }

    /**
     * 提交数组
     * @param ids
     * @return
     */
    @PostMapping("/postAarry")
    public HttpResult postAarry(String[] ids){
        return HttpResult.success("提交数组").data(ids);
    }

    /**
     * 提交数组(List)
     * @param ids
     * @return
     */
    @PostMapping("/postList")
    public HttpResult postList(@RequestBody Set<String> ids){
        return HttpResult.success("提交数组(List)").data(ids);
    }


    /**
     * 提交对象列表
     * @param ids
     * @return
     */
    @PostMapping("/postBeanList")
    public HttpResult postBeanList(List<ActionOption> ids){
        return HttpResult.success("提交对象列表").data(ids);
    }

    /**
     * 1对1
     *  一个对象包含一个子对象
     * @param cmd
     * @return
     */
    @PostMapping("/postBeanOne")
    public HttpResult postBeanOne(RoleSaveCmd cmd){
        return HttpResult.success("一个对象包含一个子对象").data(cmd);
    }

    /**
     * 1对多
     *  一个对象包含一个子对象
     * @param cmd
     * @return
     */
    @PutMapping("/postBeanMore")
    public HttpResult postBeanMore(RoleSaveCmd cmd){
        return HttpResult.success("一个对象包含多个子对象").data(cmd);
    }
    /**
     * 审核数据
     *
     * @return
     */
    @PostMapping("/authData")
    public HttpResult authData(RoleAuditCmd cmd) {

        roleCmdApp.auditCmd(cmd);
        return HttpResult.success();
    }


    /**
     * 册除数据
     * @param cmd
     * @return
     */
    @GetMapping("/deleteData")
    public HttpResult deleteData(RoleDeleteCmd cmd){
        roleCmdApp.deleteCmd(cmd);
        return HttpResult.success();
    }
    /**
     * 数据列表
     *
     * @return
     */
    @GetMapping("/getList")
    public HttpResult getList(HttpServletRequest request) {
        ParamMap params = new ParamMap();
        Pager pager = new Pager(20);
        RoleQuery query = new RoleQuery(pager);
        List<RoleDto> dataList = roleQueryApp.getRoleByPage(query);

//        return HttpResult.success(toTreeNode(zeroNode, lists));
        return HttpResult.success().put("dataList", dataList).put("pager", pager);
    }
}
