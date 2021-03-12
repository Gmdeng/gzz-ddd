package com.gzz.retail;

import com.gzz.retail.domain.Person;
import org.junit.Test;

import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestJdk8Stream {
    @Test
    public void testFunctionIdentity() {
        // "20190001","小明"
        Person studentA = new Person().setAge(10).setGender(1).setName("小明");
        Person studentB = new Person().setAge(20).setGender(2).setName("小红");
        Person studentC = new Person().setAge(30).setGender(2).setName("小丁");

        //Function.identity() 获取这个对象本身，那么结果就是Map<String,Student> 即 id->student
        //串行收集
        Map<Integer, Person> persons = Stream.of(studentA, studentB, studentC)
                .collect(Collectors.toMap(Person::getAge, Function.identity()));

        System.out.println(persons);

        //并发收集
        ConcurrentMap concurrentMaps = Stream.of(studentA, studentB, studentC)
                .parallel()
                .collect(Collectors.toConcurrentMap(Person::getAge, Function.identity()));
        System.out.println(concurrentMaps);

        Map<Integer, Person> personMaps = Stream.of(studentA, studentB, studentC)
                .collect(Collectors.toMap(Person::getAge,
                        Function.identity(),
                        BinaryOperator.maxBy(Comparator.comparing(Person::getName))));
        //可能上面比较复杂，这编写一个命令式
        //Map<String,Student>
        Map<Integer, Person> esef = Stream.of(studentA, studentB, studentC)
                .collect(Collectors.toMap(Person::getAge,
                        Function.identity(),
                        (s1, s2) -> {
                            //这里使用compareTo 方法 s1>s2 会返回1,s1==s2 返回0 ，否则返回-1
                            if (((Person) s1).getName().compareTo(((Person) s2).getName()) < -1) {
                                return s2;
                            } else {
                                return s1;
                            }
                        }));
        System.out.println(esef);
    }

}
