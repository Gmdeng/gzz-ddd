package com.gzz.retail.facade.api.admin.goods;

import com.gzz.core.response.HttpResult;
import com.gzz.retail.application.cqrs.goods.BrandCmdApplication;
import com.gzz.retail.application.cqrs.goods.BrandQueryApplication;
import com.gzz.retail.application.cqrs.goods.command.BrandAuditCmd;
import com.gzz.retail.application.cqrs.goods.command.BrandDeleteCmd;
import com.gzz.retail.application.cqrs.goods.command.BrandSaveCmd;
import com.gzz.retail.application.cqrs.goods.dto.BrandDto;
import com.gzz.retail.application.cqrs.goods.dto.BrandFormDto;
import com.gzz.retail.application.cqrs.goods.queries.BrandQuery;
import com.gzz.retail.application.cqrs.system.UserCmdApplication;
import com.gzz.retail.application.cqrs.system.UserQueryApplication;
import com.gzz.retail.application.cqrs.system.command.UserAuditCmd;
import com.gzz.retail.application.cqrs.system.command.UserDeleteCmd;
import com.gzz.retail.application.cqrs.system.command.UserSaveCmd;
import com.gzz.retail.application.cqrs.system.dto.UserDto;
import com.gzz.retail.application.cqrs.system.dto.UserFormDto;
import com.gzz.retail.application.cqrs.system.queries.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

/**
 * 商品品牌管理
 */
@Slf4j
@RestController
@RequestMapping("/admin/goods/brand")
public class BrandApi {
    @Autowired
    private BrandCmdApplication cmdApp;
    @Autowired
    private BrandQueryApplication queryApp;

    /**
     * 获取表单数据
     * @param id
     * @return
     */
    @GetMapping("/getFormData")
    public HttpResult getFormData(Long id){
        BrandFormDto dto = queryApp.getFormById(id);
        return HttpResult.success(dto);
    }

    /**
     * 获取明细数据
     * @param id
     * @return
     */
    @GetMapping("/getDetail")
    public HttpResult getDetail(@Valid Long id){
        BrandDto dto = queryApp.getDetailById(id);
        return HttpResult.success(dto);
    }

    /**
     * 分页数据列表
     *
     * @return
     */
    @PostMapping("/getDataListByPage")
    public HttpResult getDataListByPage(BrandQuery query) {
        List<BrandDto> dataList = queryApp.getBrandsByPage(query);
        return HttpResult.success().put("dataList", dataList).put("pager", query.getPager());
    }

    /**
     * 保存数据
     *
     * @return
     */
    @PostMapping("/saveData")
    public HttpResult saveData(@Validated BrandSaveCmd cmd) {
        log.info("接收到参数： {}",cmd.toString());
        cmdApp.saveCmd(cmd);
        return HttpResult.success();
    }

    /**
     * 审核数据
     *
     * @return
     */
    @PostMapping("/authData")
    public HttpResult authData(@Validated BrandAuditCmd cmd) {
        cmdApp.auditCmd(cmd);
        return HttpResult.success();
    }


    /**
     * 册除数据
     * @param cmd
     * @return
     */
    @PostMapping("/deleteData")
    public HttpResult deleteData(@Validated BrandDeleteCmd cmd){
        cmdApp.deleteCmd(cmd);
        return HttpResult.success();
    }
}
