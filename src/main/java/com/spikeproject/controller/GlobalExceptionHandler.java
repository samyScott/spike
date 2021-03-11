package com.spikeproject.controller;

import com.spikeproject.error.BusinessException;
import com.spikeproject.error.EmBusinessError;
import com.spikeproject.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * <h1>全局异常处理类</h1>
 * @author samy
 * @date 2020/1/7 15:20
 */
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonReturnType doError(HttpServletRequest request, HttpServletResponse response,Exception ex){
        ex.printStackTrace();
        Map<String,Object> responseData = new HashMap<>();
        if (ex instanceof BusinessException){
            BusinessException businessException = (BusinessException) ex;
            responseData.put("errCode",businessException.getErrCode());
            responseData.put("errMsg",businessException.getErrMsg());
        }else if (ex instanceof ServletRequestBindingException){
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg","url绑定路由问题");
        }else if (ex instanceof NoHandlerFoundException){
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg","没有找到对应的访问路径");
        }else{
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg",EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }

        return CommonReturnType.create("fail",responseData);
    }
}