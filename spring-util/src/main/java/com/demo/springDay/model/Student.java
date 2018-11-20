package com.demo.springUtil.model;

/**
 * Stream_ 使用数据模型
 * @Author youzhanghao [m13732916591_1@163.com]
 * @Date 2018/6/8 下午2:59
 */
public class Student {

    private Integer no;

    private String name;

    private String gender;

    private Float height;

    public Integer getNo() {
        return no;
    }

    public void setNo(Integer no) {
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    /**
     * 不严谨 仅供测试
     * @param no
     * @param name
     * @param gender
     * @param height
     */
    public Student(int no, String name, String gender, float height) {
        this.no = no;
        this.name = name;
        this.gender = gender;
        this.height = height;
    }
}
