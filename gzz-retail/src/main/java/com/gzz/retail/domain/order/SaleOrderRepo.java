package com.gzz.retail.domain.order;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.List;

/**
 * repository
 */
public class SaleOrderRepo {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void makeOrder(){
        /**
         *  Redis事务
         */
       List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForSet().add("key", "value1");
                // This will contain the results of all ops in the transaction
                // operations.discard(); 回滚
//                operations.watch("");//作用: 监听 key1 key2 keyN有没有变化, 如果有变化, 则事务取消
//
//                operations.unwatch(); //不加key : 取消所有 watch 监听
                return operations.exec();
            }
       });

    }
}

