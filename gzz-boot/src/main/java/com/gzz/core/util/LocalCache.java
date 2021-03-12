package com.gzz.core.util;

import com.google.common.base.Supplier;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * 本地缓存工具
 *
 * 对于一些改动频率低且调用非常频繁，可加上本地有效时间短(1分钟)的二级缓存
 */
public class LocalCache<K,V> {
    /**默认的本地缓存最大数据长度*/
    private static final long Default_Maximum_Size = 5_000;
    /**本地缓存数据长度为1    */
    private static final long Single_Size = 1;

    /**写入本地缓存后的失效时间秒数*/
    private static final long Max_Expire_After_Write = 1 * 60;


    private Cache<K, Optional<V>> cache;

    private LocalCache(){}

    /**
     * 创建本地缓存对象(创建此对象后，需要用static成员变量进行唯一初始化引用)
     * (默认为一分钟缓存)
     * @return
     */
    public static <K,V> LocalCache<K,V> create() {
        LocalCache<K,V> local = new LocalCache<K,V> ();

        local.cache =  CacheBuilder.newBuilder()
                .maximumSize(Default_Maximum_Size)
                .expireAfterWrite(Max_Expire_After_Write, TimeUnit.SECONDS)
                .build();
        return local;
    }

    /**
     * 创建本地缓存对象
     * @param expireAfterWrite 写入缓存后失效的秒数
     * @return
     */
    public static <K,V> LocalCache<K,V> create(long expireAfterWrite) {
        LocalCache<K,V> local = new LocalCache<K,V> ();

        local.cache =  CacheBuilder.newBuilder()
                .maximumSize(Default_Maximum_Size)
                .expireAfterWrite(expireAfterWrite <= 0 ?  Max_Expire_After_Write : expireAfterWrite, TimeUnit.SECONDS)
                .build();
        return local;
    }

    /**
     * 创建只能缓存一个数据元素的本地缓存对象
     * @return
     */
    public static <K,V> LocalCache<K,V> createSingleSize() {
        LocalCache<K,V> local = new LocalCache<K,V> ();

        local.cache =  CacheBuilder.newBuilder()
                .maximumSize(Single_Size)
                .expireAfterWrite(Max_Expire_After_Write, TimeUnit.SECONDS)
                .build();
        return local;
    }



    private Cache<K, Optional<V>> getCache() {
        return this.cache;
    }

    /**
     * 获取缓存k对应的值，没有值返回null
     * @param k       缓存key
     * @return
     */
    public Optional<V> get(K k) {
        return getCache().getIfPresent(k);
    }


    /**
     * 获取缓存key对应的值，若key未在本地缓存中，使用call进行初始化
     * @param k          缓存key
     * @param call    若key未在本地缓存中，使用call进行初始化。
     *                可用Lambada表达式， （） ->{ 具体逻辑,return V };
     * @return
     */
    public V get(K k, Supplier<V> call) {
        if (k == null) {
            return null;
        }
        /**
         * 由于Guava的Callable接口中，若采用过期机制，
         * 如果自带的Callable返回了null，get(xx,CallAble)便会抛出异常： CacheLoader returned null for key
         * 故采用Optional + 额外的Supplier
         */
        Optional<V>  value = get(k);
        //未放置本地缓存数据
        if (value == null) {
            V v =  call.get();
            getCache().put(k, Optional.ofNullable(v));
            return v;
        }
        return value.orElse(null);
    }
    //本地缓存，写入后20秒失效
    private static final LocalCache<String, List<String>> Cache = LocalCache.create(20);

    public static void main(String[] args) {

        //使用本地缓存
        List<String> list = Cache.get("key", () -> {
            //具体获取数据的逻辑
            List<String> strings = new ArrayList<>();
            strings.add("假装有数据");
            return strings;
        });

    }
}
