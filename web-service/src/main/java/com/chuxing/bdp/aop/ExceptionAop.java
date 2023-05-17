package com.chuxing.bdp.aop;

import com.chuxing.bdp.model.rpc.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @date 2022/11/28 15:49
 * @author huangchenguang
 * @desc validation全局异常处理类
 */
@Slf4j
@ControllerAdvice
public class ExceptionAop {

    /**
     * @date 2022/11/28 15:49
     * @author huangchenguang
     * @desc validation全局异常处理，处理报错封装成Result返回
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("参数校验失败", e);
        return Result.fail(e.getMessage());
    }

}
