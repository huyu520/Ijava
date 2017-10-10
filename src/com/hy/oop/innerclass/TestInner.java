package com.hy.oop.innerclass;

import org.junit.Test;

import com.hy.oop.innerclass.XhotelResponse.Result;

/**
 * 作用:
 * 
 * @author wb-hy292092
 * @date 2017年8月10日 下午7:33:33
 */
public class TestInner {
	@Test
	public void fun() {
		Result result = new XhotelResponse.Result();
		result.setName("123");
		result.setAge(1232);
		System.out.println(result.toString() );
	}
}
