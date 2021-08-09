package com.gzz.retail.facade.api.admin.goods;


import com.gzz.core.response.HttpResult;
import com.gzz.retail.application.cqrs.goods.GoodsSkuCmdApplication;
import com.gzz.retail.application.cqrs.goods.GoodsSkuQueryApplication;
import com.gzz.retail.application.cqrs.goods.command.AttributeSaveCmd;
import com.gzz.retail.application.cqrs.goods.command.GoodsSkuSaveCmd;
import com.gzz.retail.application.cqrs.goods.dto.AttributeDto;
import com.gzz.retail.application.cqrs.goods.dto.AttributeFormDto;
import com.gzz.retail.application.cqrs.goods.dto.GoodsSkuDto;
import com.gzz.retail.application.cqrs.goods.dto.GoodsSkuFormDto;
import com.gzz.retail.application.cqrs.goods.queries.AttributeQuery;
import com.gzz.retail.application.cqrs.goods.queries.GoodsSkuQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/admin/goods/sku")
public class GoodsSkuApi {
    @Autowired
    private GoodsSkuCmdApplication cmdApp;
    @Autowired
    private GoodsSkuQueryApplication queryApp;

    /**
     * 获取表单数据
     * @param id
     * @return
     */
    @GetMapping("/getFormData")
    public HttpResult getFormData(Long id){
        GoodsSkuFormDto dto = queryApp.getFormById(id);
        return HttpResult.success(dto);
    }

    /**
     * 获取明细数据
     * @param id
     * @return
     */
    @GetMapping("/getDetail")
    public HttpResult getDetail(@Valid Long id){
        GoodsSkuDto dto = queryApp.getDetailById(id);
        return HttpResult.success(dto);
    }

    /**
     * 分页数据列表
     *
     * @return
     */
    @PostMapping("/getDataListByPage")
    public HttpResult getDataListByPage(GoodsSkuQuery query) {
        List<GoodsSkuDto> dataList = queryApp.getGoodsSkusByPage(query);
        return HttpResult.success().put("dataList", dataList).put("pager", query.getPager());
    }

    /**
     * 保存数据
     *
     * @return
     */
    @PostMapping("/saveData")
    public HttpResult saveData(@Validated GoodsSkuSaveCmd cmd) {
        log.info("接收到参数： {}",cmd.toString());
        cmdApp.saveCmd(cmd);
        return HttpResult.success();
    }
}
