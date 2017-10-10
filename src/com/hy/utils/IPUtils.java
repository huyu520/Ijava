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

    // ��ȡMAC��ַ�ķ���
    public static String getMACAddress() {
        // �������ӿڶ��󣨼������������õ�mac��ַ��mac��ַ������һ��byte�����С�
        InetAddress ia = getInstance();
        if(ia==null)return "";
        byte[] mac;
        try {
            mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
        } catch (SocketException e) {
            return null;
        }
        // ��������ǰ�mac��ַƴװ��String
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < mac.length; i++) {
            if (i != 0) {
                sb.append("-");
            }
            // mac[i] & 0xFF ��Ϊ�˰�byteת��Ϊ������
            String s = Integer.toHexString(mac[i] & 0xFF);
            sb.append(s.length() == 1 ? 0 + s : s);
        }
        // ���ַ�������Сд��ĸ��Ϊ��д��Ϊ�����mac��ַ������
        return sb.toString().toUpperCase();
    }

	/**
	 * ��ȡIP��ַ,�������˵�IP��ַ��
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
