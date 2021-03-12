package com.gzz.core.request;

/**
 * 操作人员上下文
 */
public class OperatorContextHolder {

    public static ThreadLocal<Operator> context = new ThreadLocal<Operator>();

    public static Operator GetOperator() {
        return context.get();
    }

    public static void setOperator(Operator user) {
        context.set(user);
    }

    public static void remove() {
        context.remove();
    }

}
