package com.hy.cache.ehcache;

import org.junit.Test;

/**
 * 作用:
 * @author wb-hy292092
 * @date 2017年8月10日 上午10:44:57
 */
public class EhcacheTest {
	
	@Test
	public void function_1(){
		EhcacheUtil cache = EhcacheUtil.getInstance() ;
		cache.put("123", "huyu", "timo");
		Object result = cache.get("123", "huyu");
		System.out.println(result);
		cache.remove("123","huyu");
		result = cache.get("123", "huyu");
		System.out.println(result);
	}
}
