package com.gzz.retail.application.cqrs.goods;

import com.gzz.core.toolkit.ParamMap;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.assembler.CatalogAssembler;
import com.gzz.retail.application.assembler.dto.TreeSelectDto;
import com.gzz.retail.application.cqrs.goods.dto.CatalogDto;
import com.gzz.retail.application.cqrs.goods.dto.CatalogFormDto;
import com.gzz.retail.application.cqrs.goods.queries.CatalogQuery;
import com.gzz.retail.application.cqrs.system.queries.ModuleQuery;
import com.gzz.retail.infra.persistence.mapper.IPCatalogMapper;
import com.gzz.retail.infra.persistence.pojo.PCatalogPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/***
 * 分类
 */
@Slf4j
@Service
public class CatalogQueryApplication {
    @Autowired
    private IPCatalogMapper catalogMapper;


    /**
     * 获取表单数据
     * @param catalogId
     * @return
     */
    public CatalogFormDto getCatalogFormById(Long catalogId){
        PCatalogPo catalogPo = catalogMapper.getById(catalogId);
        CatalogFormDto catalogDto = BeanConvertUtil.convertOne( catalogPo, CatalogFormDto.class);
        return catalogDto;
    }

    /**
     * 获取详细
     * @param catalogId
     * @return
     */
    public CatalogDto getCatalogDetailById(Long catalogId){
        PCatalogPo catalogPo  = catalogMapper.getById(catalogId);
        CatalogDto catalogDto = BeanConvertUtil.convertOne(catalogPo, CatalogDto.class);
        return catalogDto;
    }

    /**
     * 获取分页面
     * @param query
     * @return
     */
    public List<CatalogDto> getCatalogByPage(CatalogQuery query){
        List<PCatalogPo> dataList = catalogMapper.findListByPage(query.toParam(), query.getPager());
        List<CatalogDto> list = BeanConvertUtil.convertList( dataList, CatalogDto.class);
        return list;
    }

    /**
     * 获取所有
     * @param query
     * @return
     */
    public List<CatalogDto> getCatalogTreeList(CatalogQuery query){
        List<PCatalogPo> dataList = catalogMapper.findList(query.toParam());
        List<CatalogDto> list = BeanConvertUtil.convertList( dataList, CatalogDto.class);

        // 转目录树
        CatalogDto parent  = new CatalogDto();
        parent.setId(0L);
        parent.setChildren(new ArrayList<>());
        CatalogAssembler.toModuleNode(list, parent);
        return parent.getChildren();
    }

    /**
     *
     * @param query
     * @return
     */
    public List<CatalogDto> getCatalogList(ModuleQuery query) {
        List<PCatalogPo> dataList = catalogMapper.findList(query.toParam());
        List<CatalogDto> list = BeanConvertUtil.convertList( dataList, CatalogDto.class);
        return list;
    }

    /**
     * 树形目录选项
     *
     * @return
     */
    public TreeSelectDto getTreeSelect(){
        List<PCatalogPo> poList = catalogMapper.findLists(new ParamMap());
        TreeSelectDto dto = new TreeSelectDto("根目录", 0L);
        CatalogAssembler.toTreeSelectNode(poList, dto);
        return dto;
    }
}
