package com.hy.entity;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 作用:
 * @author wb-hy292092
 * @date 2017年7月31日 下午5:49:13
 */
public class BaseEntity implements Serializable{
	
	public BaseEntity() {
		
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
