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
import com.gzz.retail.facade.api.admin.system.param.Rules;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

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
    @PostMapping("/saveData")
    public HttpResult saveData(@Validated RoleSaveCmd cmd) {
        log.info("接收到参数： {}",cmd.toString());
        roleCmdApp.saveCmd(cmd);
        return HttpResult.success();
    }

    /**
     * 提交数组
     * @param ids
     * @return
     *
     * @remark
     * Headers : "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8;"
     * params: ids=33&ids=DDD
     */
    @PatchMapping("/postAarry")
    public HttpResult postAarry(String[] ids){
        return HttpResult.success("提交数组").data(ids);
    }

    /**
     * 提交数组(List)
     * @param ids
     * @return
     *
     * @remark
     * Headers : "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8;"
     * params: ids=33&ids=DDD
     *  用到的qs.stringify的arrayFormat: "repeat"
     * 必须加上@RequestParam(value = "ids") 指从参数名
     */
    @PatchMapping("/postList")
    public HttpResult postList(@RequestParam(value = "ids")List<String> ids){
        return HttpResult.success("提交数组(List)").data(ids);
    }


    /**
     * 提交对象列表(BeanList)
     * @param ids
     * @return
     *
     * @remark
     * Headers : "Content-Type": "application/json;charset=utf-8"
     * params: 直接POST JSON格式的数据， 不用qs.stringify进行格式化
     * 必须加上这个@RequestBody注解参数
     */
    @PutMapping("/postBeanList")
    public HttpResult postBeanList(@RequestBody List<ActionOption> ids){
        return HttpResult.success("提交对象列表").data(ids);
    }

    /**
     * 1对1
     *  一个对象包含一个子对象
     * @param cmd
     * @return
     *
     * @remark
     *   Headers : "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8;"
     *   params: ids=33&ids=DDD&arry=[22, 23,24]&person.id=22&person.name=ricky&person.age=22
     *
     */
    @PostMapping("/postBeanOne")
    public HttpResult postBeanOne(RoleSaveCmd cmd){
        return HttpResult.success("一个对象包含一个子对象").data(cmd);
    }

    /**
     * 1对多 混合数组[]
     *  一个对象包含一个子对象
     * @param cmd
     * @return
     *
     * @remark
     * Headers : "Content-Type": "application/json;charset=utf-8"
     * params: 直接POST JSON格式的数据， 不用qs.stringify进行格式化JSON
     * 必须加上这个@RequestBody注解参数
     */
    @PutMapping("/postBeanMore")
    public HttpResult postBeanMore(@RequestBody RoleSaveCmd cmd){
        return HttpResult.success("一个对象包含多个子对象").data(cmd);
    }


    /**
     * 1对多
     * @param rules
     * @return
     *
     * @Remark
     *  Headers : "Content-Type": "application/x-www-form-urlencoded;charset=UTF-8;"
     *  params: rules[0].operate=%E5%A4%A7%E4%BA%8E&rules[0].value=22&rules[0].column=age&rules[1].operate=%E7%89%B9%E4%BA%8E&rules[1].value=8902&rules[1].column=testing
     *  需要用到 qs.stringify(request.data, {
     *         arrayFormat: "indices", // ids[0]=1&ids[1]=2
     *         allowDots: true // ids[0].id=12&ids[1].name=ricky
     *       });
     *       格式化URL
     */
    @PostMapping("/postRules")
    public HttpResult postRules(Rules rules) {
        return HttpResult.success().data(rules);
    }

    @RequestMapping("/postBeanArry")
    public HttpResult postBeanArry(ActionOption[] arry){
        return HttpResult.success("提交对象列表").data(arry);
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
     * 分页数据列表
     *
     * @return
     */
    @GetMapping("/getDataListByPage")
    public HttpResult getDataListByPage(HttpServletRequest request) {
        ParamMap params = new ParamMap();
        Pager pager = new Pager(20);
        RoleQuery query = new RoleQuery(pager);
        List<RoleDto> dataList = roleQueryApp.getRolesByPage(query);

//        return HttpResult.success(toTreeNode(zeroNode, lists));
        return HttpResult.success().put("dataList", dataList).put("pager", pager);
    }
}
