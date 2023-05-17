package com.chuxing.bdp.aop;

import com.alibaba.fastjson2.JSON;
import com.chuxing.bdp.model.rpc.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @date 2022/11/28 15:38
 * @author huangchenguang
 * @desc 全局异常处理类，处理报错封装成Result返回
 */
@Slf4j
@Aspect
@Component
public class ControllerAop {

    /**
     * @date 2022/11/28 15:41
     * @author huangchenguang
     * @desc 全局异常处理，处理报错封装成Result返回
     */
    @Around("execution(* com.chuxing.bdp.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            // 所有的方法都需要使用Result返回，void的返回值在这里不会触发aop
            Object result = joinPoint.proceed();
            try {
                log.info("请求调用成功，参数={}, 返回结果={}", JSON.toJSONString(joinPoint.getArgs()), JSON.toJSONString(result));
            } catch (Exception ignored) {
            }
            return result;
        } catch (Throwable e) {
            log.error("请求调用失败，参数={}", joinPoint.getArgs(), e);
            return Result.fail(e.getMessage());
        }
    }

}
