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
 * @desc ExceptionAop
 */
@Slf4j
@ControllerAdvice
public class ExceptionAop {

    /**
     * @date 2022/11/28 15:49
     * @author huangchenguang
     * @desc catch MethodArgumentNotValidException
     */
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.info("http invoke params check fail", e);
        return Result.fail(e.getMessage());
    }

}
