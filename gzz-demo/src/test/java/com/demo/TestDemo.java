package com.demo;


import org.junit.Test;

import java.util.function.Predicate;

public class TestDemo {
    @Test
    public void testMail(){
        System.out.println("=============================");
        Predicate<Integer> biggerThan6 = x -> x > 6;
        Predicate<Integer> lessThan3 = x -> x < 3;
        Predicate<Integer> lessThan9 = x -> x < 9;

        System.out.println("negate value=" + biggerThan6.test(7));
        System.out.println("negate value=" + biggerThan6.test(6));
        System.out.println("negate value=" + biggerThan6.test(5));
        System.out.println("=============================");
        //7比6大为true，为false
        System.out.println("negate value=" + biggerThan6.negate().test(7));
        System.out.println("negate value=" + biggerThan6.negate().test(6));
        System.out.println("negate value=" + biggerThan6.negate().test(5));
    }
}
