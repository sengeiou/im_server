package com.qingying0.aqachat.web.controller;

import com.qingying0.aqachat.dto.ResultDTO;
import com.qingying0.aqachat.exception.CustomCode;
import com.qingying0.aqachat.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class BaseController {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResultDTO<Object> handlercustomException(CustomException e) {
        e.printStackTrace();
        return ResultDTO.errorOf(e);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResultDTO<Object> handlerException(Exception e) {
        e.printStackTrace();
//        log.info("throw exception " + e);
        return ResultDTO.errorOf(CustomCode.FAIL);
    }
}
