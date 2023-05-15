package com.group3.brokebank.utils;

public class Response {
    public Response(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public Response(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public static Integer SUCCESS_CODE = 200;
    public static Integer ERROR_CODE = 500;
    public Integer status = SUCCESS_CODE;
    private String msg;
    public Object data = null;

    public static Response fail(String msg) {
        return new Response(ERROR_CODE, msg, null);
    }

    public static Response fail() {
        return new Response(ERROR_CODE, "Fail");
    }

    public static Response fail(Integer status, String msg) {
        return new Response(status, msg, null);
    }

    public static Response ok(Object data) {
        return new Response(SUCCESS_CODE, "Success", data);
    }

    public static Response ok(String msg, Object data) {
        return new Response(SUCCESS_CODE, msg, data);
    }

    public static Response ok(Integer status, String msg, Object data) {
        return new Response(status, msg, data);
    }

    public static Response ok() {
        return new Response(SUCCESS_CODE, "Success", null);
    }

    public Response(Object data) {
        this.data = data;
    }

    public Response() {

    }

    public Response(String msg, Object data) {
        this.msg = msg;
        this.data = data;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

