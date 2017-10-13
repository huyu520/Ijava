package com.hy.constant;

import com.hy.base.BaseResult;

/**
 * Created by wb-hyl282156 on 2017/6/29.
 */
public class ErrorCode {
    public static final ErrorCode DB_ERROR = new ErrorCode("DB_ERROR", "数据库调用错误");
    public static final ErrorCode SYSTEM_ERROR = new ErrorCode("SYSTEM_ERROR", "未知异常");
    public static final ErrorCode PARAM_ERROR = new ErrorCode("PARAM_ERROR", "参数错误");
    public static final ErrorCode EMPTY_RESULT = new ErrorCode("EMPTY_RESULT", "返回结果为空");
    public static final ErrorCode VERSION_OUT_OF_DATE = new ErrorCode("VERSION_OUT_OF_DATE", "版本号已过期");

    /**
     * 错误码（英文单词表示）
     */
    private String errCode;

    /**
     * 系统内部错误描述
     */
    private String errMsg;

    public ErrorCode(String errCode, String errMsg) {
        this.setErrCode(errCode);
        this.setErrMsg(errMsg);
    }

    public String getErrCode() {
        return errCode;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public void fillResult(BaseResult rs){
        rs.setSuccess(false);
        rs.setCode(this.getErrCode());
        rs.setMsg(this.getErrMsg());
    }

    public static ErrorCode buildExceptionErrorCode(Exception e){
        if(null == e){
            return null;
        }
        return new ErrorCode("THROW_EXCEPTION", e.getStackTrace().toString());
    }

    public static ErrorCode buildErrorCode(String errCode, String errMsg){
        return new ErrorCode(errCode, errMsg);
    }
}
