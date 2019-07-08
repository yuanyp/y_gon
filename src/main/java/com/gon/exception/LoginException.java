package com.gon.exception;

public class LoginException extends RuntimeException {
    private Integer code;

    public LoginException(ResultEnum resultEnum) {
        this(resultEnum.getMsg(), resultEnum.getCode());
    }

    public LoginException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
