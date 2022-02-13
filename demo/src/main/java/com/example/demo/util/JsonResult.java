package com.example.demo.util;


import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
//@AllArgsConstructor
@AllArgsConstructor
//结果的模板类
public class JsonResult<T> {
    private String code;
    private String message;
    private T data;

    public JsonResult() {
        this.code = "SUCCESS";
    }

    public JsonResult(T data) {
        this.code = "SUCCESS";
        this.data = data;
    }
    public  JsonResult(String message){
        this.code="SUCCESS";
        this.message = message;
    }

    public JsonResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public JsonResult( String message, T data) {
        this.code = "SUCCESS";
        this.message = message;
        this.data = data;
    }
}