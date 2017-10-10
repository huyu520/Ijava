package com.hy.utils.xml;

import java.io.IOException;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

/**
 * 作用:测试解析XMl文件
 * 
 * @author wb-hy292092
 * @date 2017年8月21日 下午9:25:08
 */
public class Test {

	public static void main(String[] args) throws JDOMException, IOException {
		//
		SAXBuilder saxBuilder = new SAXBuilder();
		Document document = saxBuilder.build(Test.class.getClassLoader().getResourceAsStream("test.xml"));
		Element element = document.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> list = element.getChildren("header");
		for (Element ele : list) {
			String name = ele.getAttributeValue("name");
			String maxWait = ele.getChildText("maxWait");
			@SuppressWarnings("unchecked")
			List<String> li  = ele.getChildren("serverIp");
			System.out.println("name = " + name );
			System.out.println("maxWait = " + maxWait );
			System.out.println("serverIp = " + li.toString() );
		}

	}
}
