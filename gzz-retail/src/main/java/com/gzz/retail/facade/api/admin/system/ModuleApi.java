package com.gzz.retail.facade.api.admin.system;

import com.alibaba.fastjson.JSON;
import com.gzz.core.response.HttpResult;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.core.validation.ValidationResult;
import com.gzz.core.validation.ValidationUtils;
import com.gzz.retail.application.system.ModuleCmdApplication;
import com.gzz.retail.application.system.ModuleQueryApplication;
import com.gzz.retail.application.system.command.ModuleSaveCmd;
import com.gzz.retail.facade.api.admin.system.param.ModuleParam;
import com.gzz.retail.facade.api.admin.system.vo.ModuleVo;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * 系统模块管理
 */
@RestController
@RequestMapping("/admin/system/module")
public class ModuleApi {
    @Autowired
    private IZModuleMapper izModuleMapper;
    @Autowired
    private ModuleCmdApplication moduleCmdApp;
    @Autowired
    private ModuleQueryApplication moduleQueryApp;

    /**
     * 新增数据
     *
     * @return
     */
    @PostMapping("/saveData")
    public HttpResult saveData(@Validated ModuleParam param) {
//        ValidationResult err = ValidationUtils.validate(param);
//        if (err.isHasError()) {
//            return HttpResult.fail().data(err.getErrors());
//        }
        ModuleSaveCmd cmd = BeanConvertUtil.convertOne(ModuleParam.class, ModuleSaveCmd.class, param);
        moduleCmdApp.saveCmd(cmd);
        return HttpResult.success(JSON.toJSONString(param));
    }

    /**
     * 修改数据
     * @param param
     * @return
     */
    @PostMapping("/modifyData")
    public HttpResult modifyData(@Validated ModuleParam param){
        return HttpResult.success(JSON.toJSONString(param));
    }
    /**
     * 审核数据
     *
     * @return
     */
    public HttpResult authData() {
        return HttpResult.success();
    }

    /**
     * 数据列表
     *
     * @return
     */
    @GetMapping("/getList")
    public HttpResult getList() {
        ParamMap params = new ParamMap();
        List<ZModulePo> dataList = izModuleMapper.findList(params);
        List<ModuleVo> lists = BeanConvertUtil.convertList(ZModulePo.class, ModuleVo.class, dataList, (s, t) -> {
        });
        ModuleVo zeroNode = new ModuleVo();
        zeroNode.setId(0L);
        return HttpResult.success(toTreeNode(zeroNode, lists));
    }

    /**
     * 数据列表
     *
     * @return
     */
    @GetMapping("/getTreeSelects")
    public HttpResult getTreeSelects() {
        return HttpResult.success(moduleQueryApp.getTreeSelect());
    }

    /**
     * 剃归 目录节点树
     *
     * @param node   上级节点
     * @param source 源列表
     * @return
     */
    private List<ModuleVo> toTreeNode(ModuleVo node, List<ModuleVo> source) {
        return source.stream()
                .filter(it -> it.getParentId() == node.getId())
                .map(m -> {
                    m.setChildren(toTreeNode(m, source));
                    return m;
                }).collect(Collectors.toList());
    }

}
