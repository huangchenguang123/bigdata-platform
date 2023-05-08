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
 * @desc ControllerAop
 */
@Slf4j
@Aspect
@Component
public class ControllerAop {

    /**
     * @date 2022/11/28 15:41
     * @author huangchenguang
     * @desc around aop
     */
    @Around("execution(* com.chuxing.bdp.controller.*.*(..))")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            // void can't be return, must return value
            Object result = joinPoint.proceed();
            try {
                log.info("http invoke success params ={}, result={}", JSON.toJSONString(joinPoint.getArgs()), JSON.toJSONString(result));
            } catch (Exception ignored) {
            }
            return result;
        } catch (Throwable e) {
            log.error("http invoke fail params={}", joinPoint.getArgs(), e);
            return Result.fail(e.getMessage());
        }
    }

}
