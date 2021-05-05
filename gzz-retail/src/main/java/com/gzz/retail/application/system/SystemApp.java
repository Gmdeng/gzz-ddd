package com.gzz.retail.application.system;

import com.gzz.core.toolkit.ParamMap;
import com.gzz.retail.domain.system.repo.SystemRepo;
import com.gzz.retail.domain.system.entity.Module;
import com.gzz.retail.infra.defines.types.OperateType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * TortoiseSVN
 */
@Service
public class SystemApp {
    @Autowired
    private SystemRepo systemRepo;


    public void saveModule(String moduleName) {
        Set<OperateType> operateTypes = new HashSet<>();
        operateTypes.add(OperateType.ADD);
        operateTypes.add(OperateType.AUDIT);
        operateTypes.add(OperateType.CANCEL);
        Module module = new Module();
        module.setName(moduleName);
        module.setIcon("https://wwww.g-zz.com");
        module.setCode("SUPER-ROOT");
        module.setOperates(operateTypes);
        systemRepo.saveModule(module);
    }

    public Module getModule(Long moduleId) {
        return systemRepo.getModuleById(moduleId);
    }

    /**
     * @return
     */
    public List<Module> getModuleList(ParamMap param) {

        return systemRepo.getModuleList(param);
    }
}
