package com.hy.base;



import java.io.Serializable;

import com.hy.constant.ErrorCode;



public class Result<T> extends BaseEntity implements BaseResult,Serializable {

    private static final long serialVersionUID = 3484820321805499434L;

    private T module;

    private boolean	success = true;

    private String code;

    private String msg;

    public T getModule() {
        return module;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }

    public Result<T> setModule(T module) {
        this.module = module;
        return this;
    }

    public Result<T> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public Result<T> setCode(String code) {
        this.code = code;
        return this;
    }

    public Result<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Result<T> setReslut(T t, ErrorCode code){
        code.fillResult(this);
        this.setModule(t);
        return this;
    }

    public static <T> Result<T> buildSuccessResult(T t){
        Result<T> res = new Result<>();
        res.setSuccess(true);
        res.setModule(t);
        return res;
    }

    public static <T> Result<T> buildSuccessResult(T t, String msg){
        Result<T> res = new Result<>();
        res.setSuccess(true);
        res.setModule(t);
        res.setMsg(msg);
        return res;
    }

    public static <T> Result<T> buildFailResult(ErrorCode errorCode){
        Result<T> res = new Result<>();
        res.setSuccess(false);
        res.setCode(errorCode.getErrCode());
        res.setMsg(errorCode.getErrMsg());
        return res;
    }

    public static <T> Result<T> buildFailResult(String msg){
        Result<T> res = new Result<>();
        res.setSuccess(false);
        res.setMsg(msg);
        return res;
    }

    public static <T> Result<T> buildFailResult(Result rs){
        Result<T> res = new Result<>();
        res.setSuccess(false);
        res.setCode(rs.getCode());
        res.setMsg(rs.getMsg());
        return res;
    }
}
