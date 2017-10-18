package test.hy.string;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Joiner;

/**
 * 作用:测试字符串一些操作
 * @author wb-hy292092
 * @date 2017年10月18日 下午2:43:00
 */
public class ListToString {
	
	private static final char separator = ',';
	
	private static List<String> list = new ArrayList<>();
	
	static{
		list.add("jack");
		list.add("timo");
		list.add("derek");
		list.add("lasa");
		list.add("diamond");
	}
	
	@Test
	public void listToString_1(){
		StringBuilder sb = new StringBuilder();
		for(String str : list){
			sb.append(str).append(separator);
		}
		String result = sb.toString().substring(0, sb.toString().length()-1);
		System.out.println(result);
	}
	
	@Test
	public void listToString_2(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0 ; i < list.size() ;i++){
			if(i == list.size() - 1 ){
				sb.append(list.get(i));
			}else {
				sb.append(list.get(i)).append(separator);
			}
		}
		
		System.out.println(sb.toString());
	}
	
	/**
	 * apcahe common 提供的方法
	 */
	@Test
	public void listToString_3(){
		String result = StringUtils.join(list, separator);
		System.out.println(result);
		Assert.assertTrue(result instanceof Object);
	}
	
	/**
	 * guava包提供的方法
	 */
	@Test
	public void listToString_4(){
		String result = Joiner.on(String.valueOf(separator)).join(list);
		System.out.println(result);
	}
	
	/**
	 * java8　提供stream
	 */
	@Test
	public void listToString_5(){
		String result = list.stream().collect(Collectors.joining(String.valueOf(separator)));
		System.out.println(result);
	}
	
	/**
	 * String 自带的方法
	 */
	@Test
	public void listToString_6(){
		String result = String.join(String.valueOf(separator), list);
		System.out.println(result);
	}
}
