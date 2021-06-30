package com.gzz.retail.domain.system.repo;

import com.gzz.core.exception.BizzException;
import com.gzz.core.util.BeanConvertUtil;
import com.gzz.core.util.StringUtil;
import com.gzz.retail.domain.system.entity.User;
import com.gzz.retail.domain.system.primitive.UserId;
import com.gzz.retail.infra.defines.CommStatus;
import com.gzz.retail.infra.defines.types.OperateType;
import com.gzz.retail.infra.persistence.mapper.IZUserMapper;
import com.gzz.retail.infra.persistence.pojo.ZRolePermissionPo;
import com.gzz.retail.infra.persistence.pojo.ZUserPo;
import com.gzz.retail.infra.persistence.pojo.ZUserRolePo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Component
public class UserRepo {
    @Autowired
    private IZUserMapper mapper;

    /**
     *
     * @param userId
     * @return
     */
    public User loadUser(UserId userId){
        ZUserPo po = mapper.getById(userId.getId());
        if(po == null){
            po = mapper.getByUserId(userId.getName());
        }
        if(po == null) throw new BizzException("找不该用户信息");
        User user = BeanConvertUtil.convertOne(po, User.class, (src, dest)->{
            dest.setUserId(new UserId(src.getId(), src.getUserId()));
            //dest.setStatus(CommStatus.valueOf(src.getStatus()).get());
        });
        return user;
    }
    /**
     * 审核
     * @param e
     */
    public void audit(User e){
        ZUserPo m = mapper.getById(e.getUserId().getId());
        //m.setStatus(e.getStatus().getKey());
        mapper.update(m);
    }

    /**
     * 保存数据
     */
    @Transactional
    public void save(User entity){
        ZUserPo po = BeanConvertUtil.convertOne(entity, ZUserPo.class,  (src, dest)->{
            if(src.getUserId()!=null) {
                dest.setId(src.getUserId().getId());
                dest.setUserId(src.getUserId().getName());
            }
        });
        int num =0;

        if(po.getId()== null) {
            po.setPasswd(StringUtil.randomChar(32));
            po.setSalt(StringUtil.randomCharNum(16));
            po.setStatus(CommStatus.ADD);
            num = mapper.insert(po);
        }else{
            num = mapper.update(po);
        }
        if(num ==0)
            throw new BizzException("保存数据异常");
        //
        mapper.clearRoles(po.getId());
        if(Objects.nonNull(entity.getRoles())) {
            List<ZUserRolePo> permList = BeanConvertUtil.convertList(entity.getRoles(), ZUserRolePo.class, (src, dest) -> {
                dest.setUserId(po.getId());
                dest.setRoleId(src.getId());
            });
            mapper.batchInsertRoles(permList);
        }
    }

    /**
     * 删除对象
     * @param entity
     */
    public void delete(User entity){
        mapper.delete(entity.getUserId().getId());
    }
}
