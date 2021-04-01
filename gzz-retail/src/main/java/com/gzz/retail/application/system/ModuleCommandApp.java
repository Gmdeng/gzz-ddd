package com.gzz.retail.application.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.application.dto.ModuleDto;
import com.gzz.retail.infra.persistence.mapper.IZModuleMapper;
import com.gzz.retail.infra.persistence.pojo.ZModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 模块管理
 *
 */
@Slf4j
@Service
public class ModuleCommandApp {
    @Autowired
    private IZModuleMapper moduleMapper;

    /**
     * 保存
     * @param dto
     */
    public void saveModule(ModuleDto dto){
        ZModule zModule = BeanConvertUtil.convertOne(ModuleDto.class, ZModule.class, dto, (src, dest) -> {
            log.info("=========================" + src);
        });
        if(StringUtil.isNull(zModule.getId())) {
            moduleMapper.insert(zModule);
        }else {
            moduleMapper.update(zModule);
        }
    }

}
