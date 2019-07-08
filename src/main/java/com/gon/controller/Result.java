package com.gon.controller;

import com.gon.controller.util.JsonUtil;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class Result {
    private int code = -1;
    private String error_msg = Constant._ERROR_MSG;
    private Map<String, Object> info = new HashMap<>();

    public Result() {
    }

    public Result(int code, String error_msg) {
        this.code = code;
        this.error_msg = error_msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }

    public void setSuccess(boolean success) {
        if (success) {
            this.setCode(0);
            if (StringUtils.equals(this.getError_msg(), Constant._ERROR_MSG)) {
                this.setError_msg(Constant._SUCCESS_MSG);
            }
        } else {
            this.setCode(-1);
        }
    }

    public boolean success() {
        return this.code == 0;
    }

    public Map<String, Object> getInfo() {
        return info;
    }

    public void setInfo(Map<String, Object> info) {
        this.info = info;
    }

    public void setField(String key, Object value) {
        this.info.put(key, value);
    }

    public void getField(String key, Object value) {
        this.info.get(key);
    }

    @Override
    public String toString() {
        return JsonUtil.bean2JosnStr(this);
    }
}
