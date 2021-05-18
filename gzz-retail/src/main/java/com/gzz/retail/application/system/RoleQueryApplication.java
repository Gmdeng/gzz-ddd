package com.gzz.retail.application.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.system.dto.RoleDto;
import com.gzz.retail.application.system.dto.RoleFormDto;
import com.gzz.retail.application.system.queries.RoleQuery;
import com.gzz.retail.infra.defines.CommStatus;
import com.gzz.retail.infra.persistence.mapper.IZRoleMapper;
import com.gzz.retail.infra.persistence.pojo.ZRolePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Slf4j
@Service
public class RoleQueryApplication {
    @Autowired
    private IZRoleMapper roleMapper;
    @Autowired

    /**
     * 获取表单数据
     * @param roleId
     * @return
     */
    public RoleFormDto getRoleFormById(Long roleId){
        ZRolePo zRole = roleMapper.getById(roleId);
        RoleFormDto dto = BeanConvertUtil.convertOne(zRole, RoleFormDto.class, (src, dest)->{
//            Set<Integer> opers = Arrays.stream(OperateType.values()).filter(it->{
//                return ((src.getOperate() & it.getKey()) == it.getKey());
//            }).map(m->{
//                return m.getKey();
//            }).collect(Collectors.toSet());
//            dest.setOperate(opers);
        });

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
            dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return list;
    }
}
