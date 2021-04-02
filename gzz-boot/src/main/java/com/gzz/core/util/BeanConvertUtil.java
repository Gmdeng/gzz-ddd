package com.gzz.core.util;

import org.springframework.cglib.beans.BeanCopier;

import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 对象转换
 */
public class BeanConvertUtil {
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
            // TTodo 请自行处理异常逻辑，这边简单返回null
            return null;
        }
    }

    private static <S, T> Function<S, T> copyMapper(Class<T> target, BiConsumer<S, T> consumer, BeanCopier copier) {
        return source -> copyOne(source, target, consumer, copier);
    }

    // 单个对象
    public static <S, T> T convertOne(Class<S> source, Class<T> target, S view, BiConsumer<S, T> consumer) {
        BeanCopier copier = BeanCopier.create(source, target, false);
        return copyOne(view, target, consumer, copier);
    }

    public static <S, T> T convertOne(Class<S> source, Class<T> target, S view) {
        BeanCopier copier = BeanCopier.create(source, target, false);
        return copyOne(view, target,  (src, dest) -> {}, copier);
    }
    // 集合对象

    /**
     * List<Order> orders;
     * convertList(Order.class, OrderVo.class, orders, (s, t) -> {
     * // 设置其他属性
     * t.setGoods(goo);
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
}
