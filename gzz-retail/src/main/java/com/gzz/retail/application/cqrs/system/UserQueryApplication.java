package com.gzz.retail.application.cqrs.system;

import com.gzz.core.util.BeanConvertUtil;
import com.gzz.retail.application.cqrs.system.dto.UserDto;
import com.gzz.retail.application.cqrs.system.dto.UserFormDto;
import com.gzz.retail.application.cqrs.system.queries.UserQuery;
import com.gzz.retail.infra.defines.CommStatus;
import com.gzz.retail.infra.persistence.mapper.IZUserMapper;
import com.gzz.retail.infra.persistence.pojo.ZUserPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


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
        UserFormDto dto = BeanConvertUtil.convertOne(po, UserFormDto.class);
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
