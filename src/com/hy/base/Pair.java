package com.hy.base;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * 作用:
 * @author wb-hy292092
 * @date 2017年7月31日 下午7:44:56
 */
public class Pair<K,V> implements Serializable{
	
	private static final long serialVersionUID = 2951969197470452478L;

	private K first ;
	
	private V second ;
	
	public Pair(){
	}
	
	public Pair(K first ,V second ){
		this.first = first ;
		this.second = second ;
	}

	public K getFirst() {
		return first;
	}

	public void setFirst(K first) {
		this.first = first;
	}

	public V getSecond() {
		return second;
	}

	public void setSecond(V second) {
		this.second = second;
	}
	
	public static <K,V> Pair<K, V> newPair(){
		return new Pair<K, V>() ;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
