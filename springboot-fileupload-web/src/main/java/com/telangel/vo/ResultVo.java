package com.telangel.vo;

import com.sun.org.apache.regexp.internal.RE;

/**
 * 统一返回结果
 * @author： lid
 * @date： 2019/3/21 17:26
 */
public class ResultVo<T> {

    private Result result;

    private String msg;

    private T data;

    public ResultVo(Result result){
        this.result = result;
    }

    public ResultVo(Result result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public ResultVo(Result result, T data) {
        this.result = result;
        this.data = data;
    }

    public ResultVo(Result result, String msg, T data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
