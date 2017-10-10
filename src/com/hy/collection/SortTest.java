package com.hy.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.google.common.collect.Maps;

import java.util.TreeMap;

/**
 * 作用:TreeMap默认是升序的，如果我们需要改变排序方式，则需要使用比较器：Comparator。Comparator可以对集合对象或者数组进行排序的比较器接口，实现该接口的public
 * compare(T o1,To2)方法即可实现排序，
 * 
 * @author wb-hy292092
 * @date 2017年8月2日 下午2:11:11
 */
public class SortTest {

	/**
	 * key排序
	 */
	@Test
	public void descendSortByKey() {
		// treeMap默认为升序
		Map<String, String> map = new TreeMap<>(new Comparator<String>() {
			/**
			 * 降序排序
			 */
			@Override
			public int compare(String o1, String o2) {
				return o2.compareTo(o1);
			}
		});

		map.put("b", "ccccc");
		map.put("d", "aaaaa");
		map.put("c", "bbbbb");
		map.put("a", "ddddd");
		print(map);
	}

	/**
	 * 升序排序（默认）
	 */
	@Test
	public void ascendSortByKey() {
		Map<String, String> map = Maps.newTreeMap();
		map.put("b", "ccccc");
		map.put("d", "aaaaa");
		map.put("c", "bbbbb");
		map.put("a", "ddddd");
		print(map);
	}

	/*
	 * 上面例子是对根据TreeMap的key值来进行排序的，但是有时我们需要根据TreeMap的value来进行排序。
	 * 对value排序我们就需要借助于Collections的sort(List<T> list, Comparator<? super T>
	 * c)方法，该方法根据指定比较器产生的顺序对指定列表进行排序。但是有一个前提条件，那就是所有的元素都必须能够根据所提供的比较器来进行比较，
	 */
	
	@Test
	public void ascendSortByValue() {
		Map<String, String> map = Maps.newTreeMap();
		map.put("b", "ccccc");
		map.put("d", "aaaaa");
		map.put("c", "bbbbb");
		map.put("a", "ddddd");
		List<Map.Entry<String, String>> list = new ArrayList<>(map.entrySet());
		//然后通过比较器来实现排序
		Collections.sort(list, new Comparator<Entry<String, String>>() {
			//升序
			@Override
			public int compare(Entry<String, String> o1, Entry<String, String> o2) {
				return o2.getValue().compareTo(o1.getValue());
			}
		});
		print(map);
	}
	
	
	
	
	/**
	 * 打印map
	 * 
	 * @param map
	 */
	public static void print(Map<String, String> map) {
		for (Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
}
