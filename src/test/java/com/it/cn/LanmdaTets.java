package com.it.cn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class LanmdaTets {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("张三", "李四","王五");
//        list.forEach(e->System.out.println(e));
        list.forEach(System.out::println);
        Stream.of(1,1,3,3,4,4,6,6)
                .filter(num -> num%2==0)
                .distinct()
                .forEach(System.out::print);
    }
}
