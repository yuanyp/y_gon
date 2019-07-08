package com.gon.controller.util;

import com.gon.controller.Result;

public class ResultUtil {


    public static Result error(Integer code, String msg) {
        return new Result(code, msg);
    }
}