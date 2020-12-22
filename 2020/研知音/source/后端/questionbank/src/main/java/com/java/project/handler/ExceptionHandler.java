package com.java.project.handler;

import com.java.project.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 同一异常处理  但时现在
 *
 *
 * */
@Slf4j
@ControllerAdvice
public class ExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(Exception.class)
    @ResponseBody
    public Result exception(Exception e)
    {
        e.printStackTrace();
        //System.out.println(e.getMessage())
        log.error(e.getMessage());
        return new Result().failed();
    }


}
