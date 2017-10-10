package com.hy.utils;



import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

public class IPUtils {
	private static class InnerClass {
		static InetAddress addr = null;
		static {
			try {
				addr = InetAddress.getLocalHost();
			} catch (Exception e) {
				addr = null;
			}
		}

	}

	private static InetAddress getInstance() {
		return InnerClass.addr;
	}
	
	public static String getLocalHostAddress(){
		return (null!=getInstance())?getInstance().getHostAddress().toString():"";
	}
	
	public static String getLocalHostName(){
		return (null!=getInstance())?getInstance().getHostName().toString():"";
	}
	
	public static void main(String[] args){
		System.out.println(IPUtils.getLocalHostName());
		System.out.println(IPUtils.getLocalHostAddress());
		System.out.println("localHost".replace("localHost", IPUtils.getLocalHostName()));
	}

    // 获取MAC地址的方法
    public static String getMACAddress() {
        // 获得网络接口对象（即网卡），并得到mac地址，mac地址存在于一个byte数组中。
        InetAddress ia = getInstance();
        if(ia==null)return "";
        byte[] mac;
        try {
            mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        } catch (SocketException e) {
            return null;
        }
        // 下面代码是把mac地址拼装成String
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF 是为了把byte转化为正整数
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        // 把字符串所有小写字母改为大写成为正规的mac地址并返回
        return sb.toString().toUpperCase();
    }

	/**
	 * 获取IP地址,流量器端的IP地址。
	 *
	 * @param request
	 * @return
	 */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        if (StringUtils.isNotBlank(ip) && ip.length() > 20) {
            ip = ip.substring(0, 19);
        }
        return ip;
    }
}
