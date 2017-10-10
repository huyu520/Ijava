package com.hy.enums;

/**
 * 作用:枚举测试按理
 * @author wb-hy292092
 * @date 2017年10月9日 下午8:35:04
 */
public enum WrapperEnumError {
	 //网络超时
    SOCKET_TIMEOUT("W001", "网络超时"),
    //网络链接异常
    SOCKET_CONNECT_EROOR("W002", "网络链接异常"),
    //请求参数错误
    REQUEST_ARGUMENTS_ERROR("W003", "请求参数错误"),
    //WSDL错误
    WDSL_ERROR("W004", "wsdl地址错误"),
    //Session创建失败
    SESSION_CREATE_ERROR("W006", "未知错误，session创建失败"),
    //session关闭失败
    SESSION_CLOSE_ERROR("W007", "未知错误，session关闭失败"),
    //token创建失败
    TOKEN_CREATE_ERROR("W008", "未知错误，token创建失败"),
    //token关闭失败
    TOKEN_CLOSE_ERROR("W009", "未知错误，token关闭失败"),
    //未知错误
    OTHER_ERROR("W400", "其他错误");

    private String code;
    private String msg;

    private WrapperEnumError(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static String getResponseMsg(String code) {
        for (WrapperEnumError wrapperEnumError : WrapperEnumError.values()) {
            if (code.equals(wrapperEnumError.getCode())) {
                return wrapperEnumError.getMsg();
            }
        }
        return OTHER_ERROR.getMsg();
    }

}	
