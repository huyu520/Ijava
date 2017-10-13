package com.hy.base;

/**
 * Created by wb-hyl282156 on 2017/6/29.
 */
public interface BaseResult{

    boolean isSuccess();

    BaseResult setSuccess(boolean isSuccess);

    String getCode();

    BaseResult setCode(String errorCode);

    String getMsg();

    BaseResult setMsg(String errorMsg);
}
