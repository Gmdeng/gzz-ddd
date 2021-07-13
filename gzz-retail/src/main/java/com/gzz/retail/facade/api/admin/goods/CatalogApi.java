package com.gzz.retail.facade.api.admin.goods;

import com.gzz.boot.aop.log.VisitLog;
import com.gzz.core.response.HttpResult;
import com.gzz.retail.application.cqrs.goods.CatalogCmdApplication;
import com.gzz.retail.application.cqrs.goods.CatalogQueryApplication;
import com.gzz.retail.application.cqrs.goods.command.CatalogAuditCmd;
import com.gzz.retail.application.cqrs.goods.command.CatalogDeleteCmd;
import com.gzz.retail.application.cqrs.goods.command.CatalogSaveCmd;
import com.gzz.retail.application.cqrs.goods.dto.CatalogDto;
import com.gzz.retail.application.cqrs.goods.dto.CatalogFormDto;
import com.gzz.retail.application.cqrs.goods.queries.CatalogQuery;
import com.gzz.retail.application.cqrs.system.ModuleCmdApplication;
import com.gzz.retail.application.cqrs.system.ModuleQueryApplication;
import com.gzz.retail.application.cqrs.system.command.ModuleAuditCmd;
import com.gzz.retail.application.cqrs.system.command.ModuleDeleteCmd;
import com.gzz.retail.application.cqrs.system.command.ModuleSaveCmd;
import com.gzz.retail.application.cqrs.system.dto.ModuleDto;
import com.gzz.retail.application.cqrs.system.dto.ModuleFormDto;
import com.gzz.retail.application.cqrs.system.queries.ModuleQuery;
import com.gzz.retail.facade.api.admin.system.vo.ModuleVo;
import com.gzz.retail.infra.defines.types.OperateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 商品分类管理
 */
@RestController
@RequestMapping("/admin/goods/catalog")
public class CatalogApi {
    @Autowired
    private CatalogCmdApplication catalogCmdApp;
    @Autowired
    private CatalogQueryApplication catalogQueryApp;

    /**
     * 获取表单数据
     * @param id
     * @return
     */
    @GetMapping("/getFormData")
    public HttpResult getFormData(Long id){
        CatalogFormDto dto = catalogQueryApp.getCatalogFormById(id);
        return HttpResult.success(dto);
    }

    /**
     * 获取明细数据
     * @param id
     * @return
     */
    @GetMapping("/getDetail")
    public HttpResult getDetail(@Valid  Long id){
        CatalogDto dto = catalogQueryApp.getCatalogDetailById(id);
        return HttpResult.success(dto);
    }
    /**
     * 保存数据
     *
     * @return
     */
    @PostMapping("/saveData")
    public HttpResult saveData(@Validated CatalogSaveCmd cmd) {
        catalogCmdApp.saveCmd(cmd);
        return HttpResult.success();
    }

    /**
     * 审核数据
     * @param catalogId
     * @return
     */
    @PostMapping("/authData")
    public HttpResult authData(@Valid Long catalogId) {
        CatalogAuditCmd cmd = new CatalogAuditCmd();
        cmd.setCatalogId(catalogId);
        cmd.setStatus(1);
        catalogCmdApp.auditCmd(cmd);
        return HttpResult.success();
    }

    /**
     * 册除数据
     * @param catalogId
     * @return
     */
    @PostMapping("/deleteData")
    public HttpResult deleteData(@Valid Long catalogId){
        CatalogDeleteCmd cmd = new CatalogDeleteCmd();
        cmd.setCatalogId(catalogId);
        catalogCmdApp.deleteCmd(cmd);
        return HttpResult.success();
    }

    /**
     * 数据列表
     * @return
     */
    @GetMapping("/getList")
    public HttpResult getList(HttpServletRequest request) {
        // ParamMap params = new ParamMap();
        CatalogQuery query = new CatalogQuery();
        List<CatalogDto> dataList = catalogQueryApp.getCatalogTreeList(query);

        // return HttpResult.success(toTreeNode(zeroNode, lists));
        return HttpResult.success().put("dataList", dataList);
    }

    /**
     * 数据列表
     *
     * @return
     */
    @VisitLog
    @GetMapping("/getTreeSelects")
    public HttpResult getTreeSelects() {
        return HttpResult.success(catalogQueryApp.getTreeSelect());
    }


}
