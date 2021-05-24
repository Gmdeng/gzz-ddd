package com.gzz.retail.facade.api.admin;

import com.gzz.core.response.HttpResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 菜单
 */
@Controller
@RequestMapping("/admin/menu")
public class MenuApi {
    public HttpResult getNE(){
        return HttpResult.success();
    }
}
