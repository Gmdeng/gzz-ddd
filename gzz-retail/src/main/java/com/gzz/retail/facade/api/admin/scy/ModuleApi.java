package com.gzz.retail.facade.api.admin.scy;

import com.alibaba.fastjson.JSON;
import com.gzz.core.response.HttpResult;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.core.validation.ValidationResult;
import com.gzz.core.validation.ValidationUtils;
import com.gzz.retail.application.dto.ModuleDto;
import com.gzz.retail.application.system.ModuleApp;
import com.gzz.retail.facade.api.admin.scy.param.ModuleParam;
import com.gzz.retail.facade.api.admin.scy.vo.ModuleVo;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModule;
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
@RequestMapping("/admin/scy/module")
public class ModuleApi {
    @Autowired
    private IZModuleMapper izModuleMapper;
    @Autowired
    private ModuleApp moduleApp;

    /**
     * 保存数据
     *
     * @return
     */
    @PostMapping("/saveData")
    public HttpResult saveData(@Validated ModuleParam param) {
//        ValidationResult err = ValidationUtils.validate(param);
//        if (err.isHasError()) {
//            return HttpResult.fail().data(err.getErrors());
//        }
        Consumer<Integer> consumer = x -> {
            int a = x + 2;
            System.out.println(a);
            System.out.println(a + "_");
        };
        consumer.accept(19);
        //
        ModuleDto dto = BeanConvertUtil.convertOne(ModuleParam.class, ModuleDto.class, param, (src, dest)->{

        });
        moduleApp.saveModule(dto);
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
        List<ZModule> dataList = izModuleMapper.findList(params);
        List<ModuleVo> lists = BeanConvertUtil.convertList(ZModule.class, ModuleVo.class, dataList, (s, t) -> {
        });
        ModuleVo zeroNode = new ModuleVo();
        zeroNode.setId(0L);
        return HttpResult.success(toTreeNode(zeroNode, lists));
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
