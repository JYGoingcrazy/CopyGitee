package com.sky.handler;

import com.sky.constant.MessageConstant;
import com.sky.exception.BaseException;
import com.sky.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param ex
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(BaseException ex){
        log.error("异常信息：{}", ex.getMessage());
        return Result.error(ex.getMessage());
    }

    /**
     * sql错误
     * @param sqlIntegrityConstraintViolationException
     * @return
     */
    @ExceptionHandler
    public Result exceptionHandler(SQLIntegrityConstraintViolationException sqlIntegrityConstraintViolationException){
//    Duplicate entry 'xiaojia' for key 'employee.idx_username
        String message = sqlIntegrityConstraintViolationException.getMessage();
        if (message.contains("Duplicate entry")){
            String[] s1 = message.split(" ");
//            找到客户端发送的信息
            String s2 = s1[2];
            String s = s2 + MessageConstant.ALREADY_EXIST;
            return Result.error(s);
        }
        else {
            return Result.error(MessageConstant.UNKNOWN_ERROR);
        }
    }

}
