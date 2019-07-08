package com.gon.exception;

public enum ResultEnum {

    UNKOWN_ERROR(-1, "操作失败"),
    SUCCESS(0, "成功");

    private Integer code;

    private String msg;

    private ResultEnum(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public Integer getCode() {
        return code;
    }

}