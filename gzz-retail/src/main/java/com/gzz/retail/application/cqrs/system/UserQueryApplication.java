package com.gzz.retail.application.cqrs.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.system.dto.UserDto;
import com.gzz.retail.application.cqrs.system.dto.UserFormDto;
import com.gzz.retail.application.cqrs.system.queries.UserQuery;
import com.gzz.retail.domain.system.primitive.RoleName;
import com.gzz.retail.infra.persistence.mapper.IZUserMapper;
import com.gzz.retail.infra.persistence.pojo.ZUserPo;
import com.gzz.retail.infra.persistence.pojo.ZUserRolePo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;


@Slf4j
@Service
public class UserQueryApplication {
    @Autowired
    private IZUserMapper userMapper;

    /**
     * 获取表单数据
     * @param userId
     * @returnuser
     */
    public UserFormDto getUserFormById(Long userId){
        ZUserPo po = userMapper.getById(userId);
        long[] roles = {};
        if(Objects.nonNull(po)){
            List<ZUserRolePo> list =  userMapper.findRoles(po.getId());
            // roles = list.stream().mapToLong(it->it.getRoleId()).toArray();
            roles = list.stream().mapToLong(ZUserRolePo::getRoleId).toArray();
        }
        UserFormDto dto = BeanConvertUtil.convertOne(po, UserFormDto.class);
        dto.setRoles(roles);
        return dto;
    }

    /**
     * 获取详细
     * @param userId
     * @return
     */
    public UserDto getUserDetailById(Long userId){
        ZUserPo po = userMapper.getById(userId);
        UserDto dto = BeanConvertUtil.convertOne(po, UserDto.class);
        List<RoleName> roles = null;
        if(Objects.nonNull(po)){
            List<ZUserRolePo> list =  userMapper.findRoles(po.getId());
            roles = BeanConvertUtil.convertList(list, RoleName.class, (src, dest)->{
                dest.setId(src.getRoleId());
                dest.setName(src.getRoleName());
            });
        }
        dto.setRoles(roles);
        return dto;
    }
    /**
     * 获取分页面
     * @param query
     * @return
     */
    public List<UserDto> getUsersByPage(UserQuery query){
        List<ZUserPo> dataList = userMapper.findListByPage(query.toParam(), query.getPager());
        List<UserDto> list = BeanConvertUtil.convertList( dataList, UserDto.class, (src, dest) ->{
            // dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return list;
    }
}
