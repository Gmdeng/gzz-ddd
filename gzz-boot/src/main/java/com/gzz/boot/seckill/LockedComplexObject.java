package com.gzz.boot.seckill;

import java.lang.annotation.*;

/**
 * LockedComplexObject也是参数级的注解，用于注解自定义类型的参数：
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LockedComplexObject {
    String field() default "";//含有成员变量的复杂对象中需要加锁的成员变量，如一个商品对象的商品ID

}