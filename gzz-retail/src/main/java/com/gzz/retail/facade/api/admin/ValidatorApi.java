package com.gzz.retail.facade.api.admin;

import com.gzz.core.response.HttpResult;
import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.mapper.IZRoleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModulePo;
import com.gzz.retail.infra.persistence.pojo.ZRolePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * 验证
 */
@RestController
@RequestMapping("/admin/validator")
public class ValidatorApi {
    @Autowired
    private IZModuleMapper moduleMapper;
    @Autowired
    private IZRoleMapper roleMapper;

    /**
     * 验证模块编码唯一性
     * @param code
     * @return
     */
    @GetMapping("/CheckModuleUniqueCode")
    public HttpResult CheckModuleUniqueCode(String code){
        ParamMap paramMap = new ParamMap();
        paramMap.put("code", code);
        List<ZModulePo> dataList =  moduleMapper.findList(paramMap);
        Long id =dataList.stream().findFirst().map(ZModulePo::getId).orElse(0L);
        if(id == 0L)
            return HttpResult.success();
        return HttpResult.fail().data(id);
    }

    /**
     * 验证角色编码唯一性
     * @param code
     * @return
     */
    @GetMapping("/CheckRoleUniqueCode")
    public HttpResult CheckRoleUniqueCode(String code){
        ParamMap paramMap = new ParamMap();
        paramMap.put("code", code);
        List<ZRolePo> dataList =  roleMapper.findList(paramMap);
        Long id =dataList.stream().findFirst().map(ZRolePo::getId).orElse(0L);
        if(id == 0L)
            return HttpResult.success();
        return HttpResult.fail().data(id);
    }
}
