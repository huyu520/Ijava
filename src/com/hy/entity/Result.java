package com.hy.entity;

import lombok.Data;

/**
 * 作用:
 * 
 * @author wb-hy292092
 * @date 2017年7月31日 下午5:53:10
 */
@Data
public class Result<T> extends BaseEntity implements BaseResult {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2931381837668828967L;

	private boolean isSuccess = true;

	private String errMsg;

	private String code;

	private T module;

	@Override
	public boolean isSuccess() {
		return isSuccess;
	}

	@Override
	public Result<T> setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
		return this;
	}

	@Override
	public Result<T> setMsg(String errMsg) {
		this.errMsg = errMsg;
		return this;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public Result<T> setCode(String code) {
		this.setCode(code);
		return this;
	}

	@Override
	public String getMsg() {
		return errMsg;
	}

}
