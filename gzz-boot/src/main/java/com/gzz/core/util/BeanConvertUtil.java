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
 * VO, DTO, PO对象转换工具类
 * 两个对象中相同名称相同类型属性的值自动复制。
 * 同时支持个别属性自定义SET值。
 *
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
     * 定义复制的方法 功能性的接口
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
     * 单个对象属性拷贝
     * @param source 源对象
     * @param clazz 目标对象Class
     * @param copier copier
     * @param <T> 目标对象类型
     * @param <S> 源对象类型
     * @return 目标对象
     */
    private static <T, S> T copyProperties(S source, Class<T> clazz, BeanCopier copier){
        if (null == copier)
            copier = buildCopier(source.getClass(), clazz);
        T t = null;
        try {
            t = clazz.newInstance();
            copier.copy(source, t, null);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
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

    /**
     *
     * @param source 源对象
     * @param target 目标对象类型
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @param consumer 自定义个性处理
     * @return
     */
    public static <S, T> T convertOne(S source, Class<T> target, BiConsumer<S, T> consumer) {
        Objects.requireNonNull(source, "源对象是一个空值，不能进行复制");
        BeanCopier copier = buildCopier(source.getClass(), target);
        return copyOne(source, target, consumer, copier);
    }

    /**
     * 复制并创建一个对象
     *
     * @param source 源对象
     * @param target 目标对象类型
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
     * @return
     */
    public static <S, T> T convertOne(S source, Class<T> target) {
        return convertOne(source, target,  null);
    }

    /**
     * 覆盖对象相对应的值
     *
     * @param source 源对象
     * @param target 目标对象
     * @param <S> 源对象类型
     * @param <T> 目标对象类型
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
     * @param sources 源列表
     * @param target
     * @param consumer
     * @param <S> 源列表对象类型
     * @param <T> 目标列表对象类型
     * @return
     */
    public static <S, T> List<T> convertList(List<S> sources, Class<T> target,  BiConsumer<S, T> consumer) {
        if (Objects.isNull(sources) || Objects.isNull(target) || sources.isEmpty())
            throw new IllegalArgumentException();
        BeanCopier copier = buildCopier(sources.get(0).getClass(), target);
        return  Optional.of(sources)
                .orElse(new ArrayList<>())
                .stream()
                .map(copyMapper(target, consumer, copier))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    /**
     * 列表对象拷贝
     * @param sources 源列表
     * @param target 源列表对象Class
     * @param <S> 源列表对象类型
     * @param <T> 目标列表对象类型

     * @return 目标列表
     */
    public static <S, T> List<T> convertList(List<S> sources, Class<T> target) {
        if (Objects.isNull(sources) || Objects.isNull(target) || sources.isEmpty())
            throw new IllegalArgumentException();
        BeanCopier copier = buildCopier(sources.get(0).getClass(), target);
        return Optional.of(sources)
                .orElse(new ArrayList<>())
                .stream().map(m -> copyProperties(m, target, copier))
                .collect(Collectors.toList());
    }



}
