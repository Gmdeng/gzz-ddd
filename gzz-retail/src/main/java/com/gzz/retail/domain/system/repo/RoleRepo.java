package com.gzz.retail.domain.system.repo;

import com.gzz.core.exception.BizzException;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.domain.system.entity.Role;
import com.gzz.retail.domain.system.primitive.RoleId;
import com.gzz.retail.infra.defines.CommStatus;
import com.gzz.retail.infra.persistence.mapper.IZRoleMapper;
import com.gzz.retail.infra.persistence.pojo.ZRolePermissionPo;
import com.gzz.retail.infra.persistence.pojo.ZRolePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RoleRepo {
    @Autowired
    private IZRoleMapper mapper;

    /**
     *
     * @param roleId
     * @return
     */
    public Role loadRole(RoleId roleId){
        ZRolePo po = mapper.getById(roleId.getId());
        Role role = BeanConvertUtil.convertOne(po, Role.class, (src, dest)->{
            dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return role;
    }
    /**
     * 审核
     * @param e
     */
    public void audit(Role e){
        ZRolePo m = mapper.getById(e.getRoleId().getId());
        m.setStatus(e.getStatus().getKey());
        mapper.update(m);
    }

    /**
     * 保存数据
     */
    public void save(Role entity){
        ZRolePo po = BeanConvertUtil.convertOne(entity, ZRolePo.class,  (src, dest)->{
            if(src!=null)
                dest.setId(src.getRoleId().getId());
        });
        int num =0;

        if(po.getId()== null) {
            num = mapper.insert(po);
        }else{
            num = mapper.update(po);
        }
        if(num ==0)
            throw new BizzException("保存数据异常");
        List<ZRolePermissionPo> permList = BeanConvertUtil.convertList(entity.getPermissions(), ZRolePermissionPo.class, (src, dest)->{

        });
        mapper.batchInsertPermission(permList);
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delete(Role entity){
        mapper.delete(entity.getRoleId().getId());
    }
}
