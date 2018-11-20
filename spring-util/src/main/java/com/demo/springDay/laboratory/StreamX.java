package com.demo.springUtil.laboratory;

import com.demo.springUtil.model.Student;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * StreamX
 * @author youzhanghao [m13732916591_1@163.com]
 * @Date 2018/6/8 下午2:55
 */
public class StreamX {

    public void testStream(){

        // 创建模型
        Student stuA = new Student(1, "A", "M", 184);
        Student stuB = new Student(2, "B", "G", 163);
        Student stuC = new Student(3, "C", "M", 175);
        Student stuD = new Student(4, "D", "G", 158);
        Student stuE = new Student(5, "E", "M", 170);

        List<Student> list = new ArrayList<>();
        list.add(stuA);
        list.add(stuB);
        list.add(stuC);
        list.add(stuD);
        list.add(stuE);

        // 获取对象中name为E的学生
        // for循环
        for(Student student: list){
            if("E".equals(student.getName())){
                System.out.println(student.getNo());
            }
        }

        // for循环中其实封装了Iterator迭代
        Iterator<Student> iterator = list.iterator();
        while (iterator.hasNext()){
            Student student = iterator.next();
            if("E".equals(student.getName())){
                System.out.println(student.getNo());
            }
        }

        /**
         * 整个迭代过程是这样的：首先调用iterator方法，产生一个新的Iterator对象，进而控制整
         个迭代过程，这就是外部迭代 迭代过程通过显式调用Iterator对象的hasNext和next方法完成迭代
         */

        // java8聚合操作
        list.stream()
                .filter(student -> "E".equals(student.getName()))
                .forEach(student -> System.out.println(student.getNo()));
        /**
         * stream方法的调用，其与iterator作用一样的作用一样，该方法不是返回一个控制迭代的 Iterator 对象，
         而是返回内部迭代中的相应接口。
         StreamX，其一系列的操作都是在操作Stream,直到feach时才会操作结果，这种迭代方式称为内部迭代。
         */

        /** 差异
         * 1. 迭代器提供next()、hasNext()等方法，开发者可以自行控制对元素的处理，以及处理方式，但是只能顺序处理；
         * 2.stream()方法返回的数据集无next()等方法，开发者无法控制对元素的迭代，迭代方式是系统内部实现的，
         * 同时系统内的迭代也不一定是顺序的，还可以并行，如parallelStream()方法。并行的方式在一些情况下，可以大幅提升处理的效率。
         */
    }


}
