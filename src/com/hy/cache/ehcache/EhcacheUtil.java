package com.hy.cache.ehcache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;

import com.intoms.util.cache.RedisCache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * 作用:hibernate 缓存ehcache
 * 
 * @author wb-hy292092
 * @date 2017年8月10日 上午9:52:58
 */
public class EhcacheUtil {
	private static final String path = "ehcache.xml";

	private CacheManager cacheManager;
	private URL url;
	private static EhcacheUtil cache;

	/**
	 * 构造器私有
	 * 
	 * @param path
	 */
	private EhcacheUtil(String path) {
		url = getClass().getResource(path);
		cacheManager = CacheManager.create(url);
	}

	/**
	 * 单例模式getInstance()
	 * 
	 * @return
	 */
	public static EhcacheUtil getInstance() {
		if (cache == null) {
			cache = new EhcacheUtil(path);
		}
		return cache;
	}

	/**
	 * 往缓存put值
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public void put(String cacheName, String key, Object value) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = new Element(key, value);
		cache.put(element);
	}

	/**
	 * 根据key获取value
	 * 
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public Object get(String cacheName, String key) {
		Cache cache = cacheManager.getCache(cacheName);
		Element element = cache.get(key);
		return element == null ? null : element.getObjectKey();
	}

	/**
	 * 删除key
	 * 
	 * @param cacheName
	 * @param key
	 */
	public void remove(String cacheName, String key) {
		Cache cache = getCache(cacheName);
		cache.remove(key);
	}

	/**
	 * 获取缓存
	 * 
	 * @param cacheName
	 * @return
	 */
	public Cache getCache(String cacheName) {
		return cacheManager.getCache(cacheName);
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		Object result = null;
		if (isEmpty(bytes)) {
			return null;
		}
		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			try {
				ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
				try {
					result = objectInputStream.readObject();
				} catch (ClassNotFoundException ex) {
					throw new Exception("Failed to deserialize object type", ex);
				}
			} catch (Throwable ex) {
				throw new Exception("Failed to deserialize", ex);
			}
		} catch (Exception e) {
//			logger.error("Failed to deserialize", e);
		}
		return result;
	}

	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 */
	@SuppressWarnings("unused")
	private static byte[] serialize(Object object) {
		byte[] result = null;
		if (object == null) {
			return new byte[0];
		}
		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
			try {
				if (!(object instanceof Serializable)) {
					throw new IllegalArgumentException(
							RedisCache.class.getSimpleName() + " requires a Serializable payload "
									+ "but received an object of type [" + object.getClass().getName() + "]");
				}
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
				objectOutputStream.writeObject(object);
				objectOutputStream.flush();
				result = byteStream.toByteArray();
			} catch (Throwable ex) {
				throw new Exception("Failed to serialize", ex);
			}
		} catch (Exception ex) {
//			logger.error("Failed to serialize", ex);
		}
		return result;
	}

	private static boolean isEmpty(byte[] data) {
		return (data == null || data.length == 0);
	}

//	private static final Logger logger = LoggerFactory.getLogger(EhcacheUtil.class);
}
