package com.gzz.retail.facade.api.admin;

import com.gzz.core.response.HttpResult;
import com.gzz.retail.application.cqrs.system.ModuleQueryApplication;
import com.gzz.retail.application.cqrs.system.dto.MenuDto;
import com.gzz.retail.application.cqrs.system.queries.ModuleQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜单
 */
@RestController
@RequestMapping("/admin/menu")
public class MenuApi {
    @Autowired
    private ModuleQueryApplication moduleQueryApp;
    /**
     * 菜单列表
     * @return
     */
    @GetMapping("/getMenuList")
    public HttpResult getMenuList(){
        List<MenuDto> list = moduleQueryApp.getMenuList(new ModuleQuery());
        list.add(0, new MenuDto(0L, "code", "主页", "BankOutlined", "/home"));
        return HttpResult.success().data(list);
    }
}
