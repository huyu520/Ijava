package com.hy.entity;

/**
 * 作用:
 * @author wb-hy292092
 * @date 2017年7月31日 下午5:56:37
 */
public interface BaseResult {
	
	//系统错误
	public static final String SYS_ERROR = "500";
	//参数错误
    public static final String PARAM_ERROR = "400";
    //biz错误
    public static final String BIZ_ERROR = "300";
    //未知的错误
    public static final String UNKNOW_ERROR = "600";
    //成功
    public static final String SUCCESS = "200";
    
    boolean isSuccess() ;
    
    BaseResult setSuccess(boolean isSuccess);
    
    BaseResult setMsg(String errMsg);
    
    String getMsg();
    
    String getCode();
    
    BaseResult setCode(String code );
}
