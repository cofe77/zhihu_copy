package com.meenie.controller;

public class ResResult {
    private Object data;
    private String msg;
    private Integer status;

    public ResResult() {
    }

    public ResResult(Integer status, String msg, Object data) {
        this.data = data;
        this.msg = msg;
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
