package com.hy.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.lang.StringUtils;

import com.google.common.collect.Maps;

/**
 * 作用:threadLocal 工具类
 * 
 * @author wb-hy292092
 * @date 2017年10月9日 下午7:48:34
 */
public class ThreadLocalUtils {
	private static ThreadLocal<Map<String, Object>> threadLocal = new ThreadLocal<Map<String, Object>>() {
		protected Map<String, Object> initialValue() {
			return new ConcurrentHashMap<>();
		};
	};

	public static Map<String, Object> getThreadLocal() {
		return threadLocal.get();
	}

	public static <T> T get(String key) {
		return get(key, null);
	}

	@SuppressWarnings("unchecked")
	public static <T> T get(String key, T defaultValue) {
		Map<String, Object> map = getThreadLocal();
		return (T) map.get(key) == null ? defaultValue : (T) map.get(key);
	}

	public static void set(String key, String value) {
		Map<String, Object> map = getThreadLocal();
		map.put(key, value);
	}

	public static void set(Map<String, Object> defaultMap) {
		Map<String, Object> map = getThreadLocal();
		map.putAll(defaultMap);
	}

	public static void removeAll() {
		threadLocal.remove();
	}

	public static void remove(String key) {
		Map<String, Object> map = getThreadLocal();
		if (map.containsKey(key)) {
			map.remove(key);
		}
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, T> getMapsByPrefix(String prefix) {
		Map<String, T> vars = Maps.newHashMap();
		if (StringUtils.isEmpty(prefix)) {
			return vars;
		}

		Map<String, T> map = (Map<String, T>) getThreadLocal();
		Set<Map.Entry<String, T>> set = map.entrySet();

		for (Entry<String, T> entry : set) {
			Object key = entry.getKey();
			if (key instanceof String) {
				if (((String) key).startsWith(prefix)) {
					vars.put((String) key, entry.getValue());
				}
			}
		}
		return vars;
	}

	@SuppressWarnings("rawtypes")
	public static void clear(String prefix) {
		if (StringUtils.isEmpty(prefix)) {
			return;
		}
		
		Map<String, Object> map = getThreadLocal();

		Set<Entry<String, Object>> set = map.entrySet();
		List<String> removeKeys = new ArrayList<>();

		for (Map.Entry entry : set) {
			Object key = entry.getKey();
			if (key instanceof String) {
				if (((String) key).startsWith(prefix)) {
					removeKeys.add((String) key);
				}
			}
		}
		for (String key : removeKeys) {
			map.remove(key);
		}
	}
}
