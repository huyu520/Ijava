package com.hy.oop.innerclass;

import org.apache.commons.lang.builder.ToStringBuilder;

import lombok.Data;

/**
 * 作用:
 * 
 * @author wb-hy292092
 * @date 2017年8月10日 下午7:24:55
 */
@Data
public class XhotelResponse {

	private XhotelResponse.Result result;

	public XhotelResponse() {
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Data
	public static class Result {
		private String name;
		private int age;
		
	}
}
