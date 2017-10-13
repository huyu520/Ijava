package com.hy.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hy.base.Result;

/**
 * 
 * 作用:Result 工具处理类
 * @author wb-hy292092
 * @date 2017年10月13日 下午4:21:36
 */
public class ResultUtils {

    protected static Logger logger = LoggerFactory.getLogger(ResultUtils.class);

    public static <T> Result<T> createFaultExp(String faultMsg, Exception e) {
        StringBuilder msgSb = new StringBuilder();
        msgSb.append(faultMsg);
        if (e != null) {
            logger.error(faultMsg, e);
            msgSb.append(" " + e.getMessage());
        }
        return createFaultResult(msgSb.toString());
    }

    public static <T> Result<T> createFaultResult(String msg) {
        return createResult(false, null, null, msg);
    }

    public static <T> Result<T> createFaultResult(String code, String msg) {
        return createResult(false, null, code, msg);
    }

    public static <T> Result<T> createSuccessResult(T data) {
        return createSuccessResult(data, null, null);
    }

    public static <T> Result<T> createSuccessResult(T data, String code, String msg) {
        return createResult(true, data, code, msg);
    }

    private static <T> Result<T> createResult(boolean success, T data, String code, String msg) {
        Result<T> result = new Result<T>();
        result.setCode(code);
        result.setMsg(msg);
        result.setSuccess(success);
        result.setModule(data);
        return result;
    }
}
