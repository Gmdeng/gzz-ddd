package com.gzz.retail.application.cqrs.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.system.dto.RoleDto;
import com.gzz.retail.application.cqrs.system.dto.RoleFormDto;
import com.gzz.retail.application.cqrs.system.queries.RoleQuery;
import com.gzz.retail.infra.defines.CommStatus;
import com.gzz.retail.infra.defines.types.OperateType;
import com.gzz.retail.infra.persistence.mapper.IZRoleMapper;
import com.gzz.retail.infra.persistence.pojo.ZRolePermissionPo;
import com.gzz.retail.infra.persistence.pojo.ZRolePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 */
@Slf4j
@Service
public class RoleQueryApplication {
    @Autowired
    private IZRoleMapper roleMapper;

    /**
     * 获取表单数据
     * @param roleId
     * @return
     */
    public RoleFormDto getRoleFormById(Long roleId){
        ZRolePo zRole = roleMapper.getById(roleId);
        RoleFormDto dto = BeanConvertUtil.convertOne(zRole, RoleFormDto.class);
        List<ZRolePermissionPo> permissions = roleMapper.findPermissions(roleId);
        HashMap<Long, Set<Integer>> permissionsMap = new HashMap<>();
        permissions.forEach(item->{
            Set<Integer> opers = Arrays.stream(OperateType.values()).filter(it->{
                return ((item.getHasPower() & it.getKey()) == it.getKey());
            }).map(m->{
                return m.getKey();
            }).collect(Collectors.toSet());
            permissionsMap.put(item.getId(), opers);
        });

        dto.setPermissions(permissionsMap);
        return dto;
    }

    /**
     * 获取详细
     * @param roleId
     * @return
     */
    public RoleDto getRoleDetailById(Long roleId){
        ZRolePo zRole = roleMapper.getById(roleId);
        RoleDto dto = BeanConvertUtil.convertOne(zRole, RoleDto.class,  (src, dest)->{
//            Set<OperateType> opers = Arrays.stream(OperateType.values()).filter(it->{
//                return ((src.getOperate() & it.getKey()) == it.getKey());
//            }).collect(Collectors.toSet());
//            dest.setOperate(opers);
            dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return dto;
    }

    /**
     * 获取分页面
     * @param query
     * @return
     */
    public List<RoleDto> getRoleByPage(RoleQuery query){
        List<ZRolePo> dataList = roleMapper.findListByPage(query.toParam(), query.getPager());
        List<RoleDto> list = BeanConvertUtil.convertList( dataList, RoleDto.class, (src, dest) ->{
//            Set<OperateType> opers = Arrays.stream(OperateType.values()).filter(it->{
//                return ((src.getOperate() & it.getKey()) == it.getKey());
//            }).collect(Collectors.toSet());
//            dest.setOperate(opers);
            // dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return list;
    }
}
