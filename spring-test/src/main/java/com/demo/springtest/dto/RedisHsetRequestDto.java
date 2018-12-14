package com.demo.springtest.dto;

/**
 * @author youzhanghao [m13732916591_1@163.com]
 * @description
 * @date 2018/11/2 下午2:43
 */
public class RedisHsetRequestDto {

    private String key;

    private String field;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
