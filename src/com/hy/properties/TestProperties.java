package com.hy.properties;

import java.util.Properties;

import org.junit.Test;

/**
 * 作用:
 * 
 * @author wb-hy292092
 * @date 2017年8月31日 下午5:03:48
 */
public class TestProperties {

	private static final String PATH = "domain.properties";

	private String HOTELDOMAN;

	private String EBOOKINGDOMAIN;

	@Test
	public void test() {
		Properties properties = PropertiesUtils.getProperty(PATH);
		HOTELDOMAN = properties.getProperty("hotelDomain");
		EBOOKINGDOMAIN = properties.getProperty("ebookingDomain");
		System.out.println("EBOOKINGDOMAIN:" + EBOOKINGDOMAIN);
		System.out.println("HOTELDOMAN:" + HOTELDOMAN);
	}

}
