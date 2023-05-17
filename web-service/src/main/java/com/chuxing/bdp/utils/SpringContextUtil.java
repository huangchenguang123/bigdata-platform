package com.chuxing.bdp.utils;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @date 2023/5/8 11:33
 * @author huangchenguang
 * @desc spring工具类
 */
@Component
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * @date 2023/5/8 11:38
     * @author huangchenguang
     * @desc 初始化application context
     */
    @Override
    public void setApplicationContext(@NotNull ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * @date 2023/5/8 11:39
     * @author huangchenguang
     * @desc 根据名称获取bean
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

}

