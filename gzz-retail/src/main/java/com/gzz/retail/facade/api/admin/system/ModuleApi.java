package com.gzz.retail.facade.api.admin.system;

import com.alibaba.fastjson.JSON;
import com.gzz.boot.aop.log.VisitLog;
import com.gzz.core.response.HttpResult;
import com.gzz.core.toolkit.Pager;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.system.ModuleCmdApplication;
import com.gzz.retail.application.system.ModuleQueryApplication;
import com.gzz.retail.application.system.command.ModuleSaveCmd;
import com.gzz.retail.application.system.dto.ModuleDto;
import com.gzz.retail.application.system.queries.ModuleQuery;
import com.gzz.retail.facade.api.admin.system.param.ModuleParam;
import com.gzz.retail.facade.api.admin.system.vo.ModuleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统模块管理
 */
@RestController
@RequestMapping("/admin/system/module")
public class ModuleApi {

    @Autowired
    private ModuleCmdApplication moduleCmdApp;
    @Autowired
    private ModuleQueryApplication moduleQueryApp;

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("/getDetail")
    public HttpResult getDetail(Long id){
        ModuleDto dto = moduleQueryApp.getModuleById(id);
        return HttpResult.success(dto);
    }
    /**
     * 新增数据
     *
     * @return
     */
    @PostMapping("/saveData")
    public HttpResult saveData(@Validated ModuleSaveCmd cmd) {
//        ValidationResult err = ValidationUtils.validate(param);
//        if (err.isHasError()) {
//            return HttpResult.fail().data(err.getErrors());
//        }

        moduleCmdApp.saveCmd(cmd);
        return HttpResult.success();
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
    @PostMapping("/authData")
    public HttpResult authData() {
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
        ModuleQuery query = new ModuleQuery();
        Pager pager = new Pager(20);
        query.setPager(pager);
        List<ModuleDto> dataList = moduleQueryApp.getModuleByPage(query);
//        List<ZModulePo> dataList = izModuleMapper.findList(params);
//        List<ModuleVo> lists = BeanConvertUtil.convertList(ZModulePo.class, ModuleVo.class, dataList, (s, t) -> {
//        });
//        ModuleVo zeroNode = new ModuleVo();
//        zeroNode.setId(0L);
//        return HttpResult.success(toTreeNode(zeroNode, lists));
        return HttpResult.success().put("dataList", dataList).put("pager", pager);
    }

    /**
     * 数据列表
     *
     * @return
     */
    @VisitLog
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
