package com.gon.exception;

import com.gon.controller.Result;
import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

//统一在这个类中处理异常，全局拦截指定的异常
@ControllerAdvice
public class ExceptionHandle {

    private final Logger logger = Logger.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)  //申明捕获那个异常类
    @ResponseBody  //返回给浏览器的是一个json格式，上面又没有@RestController，所以在此申明@ResponseBody
    public Result handle(Exception e) {
        Result result = new Result();
        result.setCode(ResultEnum.UNKOWN_ERROR.getCode());
        result.setError_msg(ResultEnum.UNKOWN_ERROR.getMsg());
        logger.error("【系统异常】", e);
        return result;
    }
}