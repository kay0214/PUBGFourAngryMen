/*
 * @Copyright: 2005-2018 www.hyjf.com. All rights reserved.
 */
package com.pubg.analysis.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author sunpeikai
 * @version EntityUtil, v0.1 2020/7/10 11:12
 * @description
 */
@Slf4j
public class EntityUtil {

    /**
     * @description 循环copy属性
     * @auth sunpeikai
     * @param
     * @return
     */
    public static <S, T> List<T> copyBeans(List<S> sources, Class<T> clazz) {
        return sources.stream().map(source -> copyBean(source, clazz)).collect(Collectors.toList());
    }

    /**
     * @description copy属性
     *      必须是字段名相同且字段类型相同
     *      其实BeanUtils.copyProperties使用反射原理实现,效率比较低
     * @auth sunpeikai
     * @param
     * @return
     */
    public static <S, T> T copyBean(S s, Class<T> clazz) {
        if (s == null) {
            return null;
        }
        try {
            T t = clazz.newInstance();
            BeanUtils.copyProperties(s, t);
            return t;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error("拷贝属性异常", e);
            throw new RuntimeException("拷贝属性异常");
        }
    }
}
