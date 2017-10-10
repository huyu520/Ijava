package com.hy.Thread;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 作用:本地线程local
 * @author wb-hy292092
 * @date 2017年9月5日 下午4:47:17
 */
public class MyThreadLocal {
	
	private static Map<Thread, Object> threadLocal = Collections.synchronizedMap(new HashMap<>());
	
	/**
	 * 
	 * @param object
	 */
	public void set(Object object){
		threadLocal.put(Thread.currentThread(), object);
	}
	
	/**
	 * 
	 * @return
	 */
	public Object get(){
		Thread currentThread = Thread.currentThread();
		Object object = threadLocal.get(currentThread);
		
		if(object == null && !threadLocal.containsKey(currentThread)){
			object = initValue();
			threadLocal.put(currentThread, object);
		}
		return object ;
	}
	
	/**
	 * 
	 * @return
	 */
	public Object initValue(){
		return null;
	}
	
	/**
	 * 
	 */
	public void remove(){
		threadLocal.remove(Thread.currentThread());
	}
}
