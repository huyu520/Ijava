package com.hy.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.junit.Test;

/**
 * 作用:IP测试相关
 * @author wb-hy292092
 * @date 2017年8月9日 上午11:55:18
 */
public class IpUtils {
	private static class Address{
		static InetAddress address = null;
		static{
			try {
				address = InetAddress.getLocalHost() ;
			} catch (UnknownHostException e) {
				address = null ;
			}
		}
	}
	
	private static InetAddress getInstance(){
		return Address.address ; 
	}
	
	public static String getLocalHostAddress(){
		return (getInstance() != null ? getInstance().getHostAddress().toString() : "" );
	}
	
	public static String getLocalHostName(){
		return (getInstance() != null ? getInstance().getHostName().toString() : "");
	}
	
	@Test
	public void test(){
		System.out.println(IpUtils.getLocalHostAddress());
		System.out.println(IpUtils.getLocalHostName());
		System.out.println(IpUtils.getInstance());
	}
}
