package com.example.demo.exception;

import com.example.demo.util.JsonResult;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

//全局公共异常处理类,接收错误异常
@ControllerAdvice //处理异常
public class CommonExceptionHandler {
    //异常处理程序，指定处理通用错误
    @ExceptionHandler(Throwable.class)
    @ResponseBody//响应数据到前端
    public JsonResult<Map<String,String>> exceptionHandler(Throwable e) {
    e.printStackTrace();//错误打印控制台
        System.out.println("全局异常拦截");
        //拿到错误消息并返回给浏览器
        return new JsonResult<>("ERROR",e.getMessage());
    }

    //绑定异常处理程序，指定处理绑定错误
    @ExceptionHandler(BindException.class)
    @ResponseBody//响应数据到前端
    public JsonResult<Map<String,String>> exceptionHandler(BindException e) {
        e.printStackTrace();//错误打印控制台
        System.out.println("绑定异常拦截器");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        //拿到错误消息并返回给浏览器
        return new JsonResult<>("ERROR",message);
    }
}
