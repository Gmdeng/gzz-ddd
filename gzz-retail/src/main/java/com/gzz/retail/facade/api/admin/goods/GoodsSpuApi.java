package com.gzz.retail.facade.api.admin.goods;

import com.gzz.core.response.HttpResult;
import com.gzz.retail.application.cqrs.goods.GoodsSpuCmdApplication;
import com.gzz.retail.application.cqrs.goods.GoodsSpuQueryApplication;
import com.gzz.retail.application.cqrs.goods.command.AttributeSaveCmd;
import com.gzz.retail.application.cqrs.goods.command.GoodsSpuSaveCmd;
import com.gzz.retail.application.cqrs.goods.dto.*;
import com.gzz.retail.application.cqrs.goods.queries.AttributeQuery;
import com.gzz.retail.application.cqrs.goods.queries.GoodsSpuQuery;
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
@RequestMapping("/admin/goods/spu")
public class GoodsSpuApi {
    @Autowired
    private GoodsSpuCmdApplication cmdApp;
    @Autowired
    private GoodsSpuQueryApplication queryApp;

    /**
     * 获取表单数据
     * @param id
     * @return
     */
    @GetMapping("/getForData")
    public HttpResult getFormData(Long id){
        GoodsSpuFormDto dto = queryApp.getFormById(id);
        return HttpResult.success(dto);
    }

    /**
     * 获取明细数据
     * @param id
     * @return
     */
    @GetMapping("/getDetail")
    public HttpResult getDetail(@Valid Long id){
        GoodsSpuDto dto = queryApp.getDetailById(id);
        return HttpResult.success(dto);
    }

    /**
     * 分页数据列表
     *
     * @return
     */
    @PostMapping("/getDataListByPage")
    public HttpResult getDataListByPage(GoodsSpuQuery query) {
        List<GoodsSpuDto> dataList = queryApp.getGoodsSpusByPage(query);
        return HttpResult.success().put("dataList", dataList).put("pager", query.getPager());
    }

    /**
     * 保存数据
     *
     * @return
     */
    @PostMapping("/saveData")
    public HttpResult saveData(@Validated GoodsSpuSaveCmd cmd) {
        log.info("接收到参数： {}",cmd.toString());
        cmdApp.saveCmd(cmd);
        return HttpResult.success();
    }
}
