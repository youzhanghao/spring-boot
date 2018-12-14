package com.demo.springUtil.redis; /**
 * @Title: JasonUtil.java
 * @Package cn.enncloud.core
 * @Description: JsonUtil
 * @author alexlu
 * @date 2017年4月28日
 * @version V1.0
 */

import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @ClassName: JsonUtil
 * @Description:JsonUtil
 * @author alexlu
 * @date 2017年4月28日
 *
 */

public class JsonUtil {
    public static <T> List<T> formatList(String jaosnStr, Class<T> clazz) {
        List<T> list = null;
        try {
            if (StringUtils.isEmpty(jaosnStr)) {
                return null;
            }
            List<Object> lst = JsonFormatter.toObject(jaosnStr, List.class);
            if (lst.size() > 0) {
                list = lst.stream().map(j -> formatObject(j, clazz)).collect(Collectors.toList());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return list;
    }

    public static <T> T formatObject(Object obj, Class<T> clazz) {
        T t = null;
        try {
            t = JsonFormatter.toObject(JsonFormatter.toJsonString(obj), clazz);
        } catch (Exception e) {
            return null;
        }
        return t;
    }
}
