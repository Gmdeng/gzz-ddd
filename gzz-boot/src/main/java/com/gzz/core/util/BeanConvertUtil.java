package com.gzz.core.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.beans.BeanCopier;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 对象转换
 */
@Slf4j
public class BeanConvertUtil {
    /**
     * 使用map存储BeanCopier实例，
     */
    private static final Map<String, BeanCopier> BEAN_COPIER_CACHE = new ConcurrentHashMap<>();

    /**
     * 生成BeanCopier存放的KEY。
     * @param source
     * @param target
     * @return
     */
    private static String buildBeanKey(String source, String target){
        return source +"_"+ target;
    }

    /**
     * 创建 copier
     * @param sourceClass
     * @param targetClass
     * @return
     */
    private static BeanCopier buildCopier(Class<?> sourceClass, Class<?> targetClass){
        BeanCopier copier;
        String key = buildBeanKey(sourceClass.getClass().toString(), targetClass.getClass().toString());
        if(BEAN_COPIER_CACHE.containsKey(key)){
            copier = BEAN_COPIER_CACHE.get(key);
        }else{
            copier = BeanCopier.create(sourceClass, targetClass, false);
            // BEAN_COPIER_CACHE.put(key, copier);
            BEAN_COPIER_CACHE.putIfAbsent(key, copier);
        }
        return copier;
    }

    /**
     * 复制并返回新的对象
     *
     * @param source
     * @param target
     * @param consumer
     * @param copier
     * @param <S>
     * @param <T>
     * @return
     */
    private static <S, T> T copyOne(S source, Class<T> target, BiConsumer<S, T> consumer, BeanCopier copier) {
        try {
            T t = target.newInstance();
            copier.copy(source, t, null);
            // TTodo consumer可以传空，即只做复制，不做特异化操作
            if (consumer != null) {
                consumer.accept(source, t);
            }
            return t;
        } catch (Exception e) {
            log.error("创建/复制对象异常：{}", e.getMessage());
            // TTodo 请自行处理异常逻辑，这边简单返回null
            return null;
        }
    }

    /**
     *
     * @param target
     * @param consumer
     * @param copier
     * @param <S>
     * @param <T>
     * @return
     */
    private static <S, T> Function<S, T> copyMapper(Class<T> target, BiConsumer<S, T> consumer, BeanCopier copier) {
        return source -> copyOne(source, target, consumer, copier);
    }

    /**
     * 是在Java8中，Stream流式处理集合功能相当强大，但是其中的distinct()却不提供针对对象属性字段进行去重的操作，不得不说略显可惜，遂有了这一小段代码，
     * 这段代码通过Stream的filter()方法进行对针对某一个属性的过滤
     * @param keyExtractor
     * @param <T>
     * @return
     */
    public static <T> Predicate<T> distinctByKey(Function<T, String> keyExtractor) {
        Set<String> set = ConcurrentHashMap.newKeySet();
        return t -> set.add(keyExtractor.apply(t));
    }

    // 单个对象
    public static <S, T> T convertOne(S source, Class<T> target, BiConsumer<S, T> consumer) {
        BeanCopier copier = buildCopier(source.getClass(), target);
        return copyOne(source, target, consumer, copier);
    }

    /**
     * 复制并创建一个对象
     *
     * @param source
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T convertOne(S source, Class<T> target) {
        BeanCopier copier = buildCopier(source.getClass(), target);
        return copyOne(source, target,  null, copier);
    }

    /**
     * 覆盖对象相对应的值
     *
     * @param source
     * @param target
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> T copyOver(S source, T target) {
        try {
            BeanCopier copier = buildCopier(source.getClass(), target.getClass());
            copier.copy(source, target, null);
        }catch (Exception e){
            log.error("覆盖对象异常：{}", e.getMessage());
        }
        return target;
    }

    /**
     * 集合对象
     * List<Order> orders;
     * convertList(Order.class, OrderVo.class, orders, (s, t) -> {
     * // 设置其他属性
     * t.setGoods(goods);
     * // 特殊属性转化
     * t.setOrderTimeString(s.getOrderTime().toString());
     * });
     *
     * @param source
     * @param target
     * @param lists
     * @param consumer
     * @param <S>
     * @param <T>
     * @return
     */
    public static <S, T> List<T> convertList(Class<S> source, Class<T> target, List<S> lists, BiConsumer<S, T> consumer) {
        BeanCopier copier = BeanCopier.create(source, target, false);
        return lists.stream()
                .map(copyMapper(target, consumer, copier))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 列表对象拷贝
     * @param sources 源列表
     * @param clazz 源列表对象Class
     * @param <T> 目标列表对象类型
     * @param <M> 源列表对象类型
     * @return 目标列表
     */
    public static <T, M> List<T> copyList(List<M> sources, Class<T> clazz) {
        if (Objects.isNull(sources) || Objects.isNull(clazz) || sources.isEmpty())
            throw new IllegalArgumentException();
        BeanCopier copier = BeanCopier.create(sources.get(0).getClass(), clazz, false);
        return Optional.of(sources)
                .orElse(new ArrayList<>())
                .stream().map(m -> copyProperties(m, clazz, copier))
                .collect(Collectors.toList());
    }

    /**
     * 单个对象属性拷贝
     * @param source 源对象
     * @param clazz 目标对象Class
     * @param copier copier
     * @param <T> 目标对象类型
     * @param <M> 源对象类型
     * @return 目标对象
     */
    private static <T, M> T copyProperties(M source, Class<T> clazz, BeanCopier copier){
        if (null == copier){
            copier = BeanCopier.create(source.getClass(), clazz, false);
        }
        T t = null;
        try {
            t = clazz.newInstance();
            copier.copy(source, t, null);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

}
