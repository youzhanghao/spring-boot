package com.demo.springUtil.laboratory;

import java.util.ArrayList;

/**
 * @Author youzhanghao [m13732916591_1@163.com]
 * @Date 2018/8/15 上午11:00
 */
public class VariableShare {

    /**
     * new  操作 生成一个新对象   = 为指针引用  指向同一个内存地址
     * @param args
     */
    public static void main(String[] args) {
        ArrayList<String> a1 = new ArrayList<>();
        a1.add("xx");
        a1.add("yy");
        ArrayList<String> a3 = new ArrayList<>();
        a3.addAll(a1);
        ArrayList<String> a2 = a1;
        a2.add("zz");

        System.out.println("a1:" + a1);
        System.out.println("a2:" + a2);
        System.out.println("a3:" + a3);
    }
}
