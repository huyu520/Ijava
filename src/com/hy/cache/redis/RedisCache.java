package com.hy.cache.redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import com.hy.cache.Cache;
import com.intoms.util.config.RedisConf;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

/**
 * 作用:Redis缓存的封装
 * 
 * @author wb-hy292092
 * @date 2017年8月12日 下午4:10:34
 */
public class RedisCache implements Cache {

//	private Logger logger = LoggerFactory.getLogger(getClass());

	private static RedisCache instance = new RedisCache();

	private ShardedJedisPool pool;

	public static RedisCache getInstance() {
		return instance;
	}

	private RedisCache() {
		try {
			init(null, null);
//			logger.info("init redisCache success!");
		} catch (Exception e) {
//			logger.error("init redisCache fail!", e);
		}
	}

	private RedisCache(JedisPoolConfig redisConfig, List<JedisShardInfo> shards) {
		try {
			init(redisConfig, shards);
//			logger.info("init redisCache success!");
		} catch (Exception e) {
//			logger.error("init redisCache fail!", e);
		}
	}

	/**
	 * 初始化jedis
	 * 
	 * @param redisConfig
	 * @param shards
	 */
	public void init(JedisPoolConfig redisConfig, List<JedisShardInfo> shards) {
		if (redisConfig == null) {
			redisConfig = new JedisPoolConfig();
			redisConfig.setMaxTotal(RedisConf.getInstance().getMaxActive());
			redisConfig.setMaxIdle(RedisConf.getInstance().getMaxIdle());
			redisConfig.setMaxWaitMillis(RedisConf.getInstance().getMaxWait() * 1000);
			redisConfig.setTestOnBorrow(false);
		}
		if (shards == null) {
			shards = new ArrayList<JedisShardInfo>();
			for (int i = 0; i < RedisConf.getInstance().getServerIp().size(); i++) {
				String ip = RedisConf.getInstance().getServerIp().get(i);
				int port = RedisConf.getInstance().getServerPort().get(i);
				JedisShardInfo info = new JedisShardInfo(ip, port);
				info.setPassword(RedisConf.getInstance().getServerPass().get(i));
				shards.add(info);
			}
		}
		pool = new ShardedJedisPool(redisConfig, shards);
	}

	/**
	 * 销毁池
	 */
	public void destory() {
		pool.destroy();
	}

	/**
	 * 序列化
	 * 
	 * @param value
	 * @return
	 */
	private <T> byte[] toBytes(T value) {
		ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
		ObjectOutputStream out;
		byte[] bytes = null;
		try {
			out = new ObjectOutputStream(byteOut);
			out.writeObject(value);
			bytes = byteOut.toByteArray();
		} catch (IOException e) {
//			logger.error("redisCahe toBytes error", e);
		}
		return bytes;
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private <T> T fromBytes(byte[] bytes) {
		if (bytes == null || bytes.length == 0) {
			return null;
		}
		T obj = null;
		try {
			ByteArrayInputStream byteIn = new ByteArrayInputStream(bytes);
			ObjectInputStream in = new ObjectInputStream(byteIn);
			obj = (T) in.readObject();
		} catch (Exception e) {
//			logger.error("redisCahe fromBytes error", e);
		}
		return obj;
	}

	@Override
	public <T> T get(String key) {
		return get(key, 0);
	}

	/**
	 * 根据key取value
	 * 
	 * @param key
	 * @param dbIndex
	 * @return
	 */
	public <T> T get(String key, int dbIndex) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		T value = fromBytes(jedis.get(key.getBytes()));
		jedis.close();
		return value;
	}

	/**
	 * 选择DB索引
	 * 
	 * @param jedis
	 * @param dbIndex
	 */
	private void selectDB(ShardedJedis jedis, int dbIndex) {
		Collection<Jedis> jedisList = jedis.getAllShards();
		Iterator<Jedis> ite = jedisList.iterator();
		while (ite.hasNext()) {
			Jedis curJedis = ite.next();
			curJedis.select(dbIndex);
		}
	}

	/***
	 * 根据key取得map集合（一对多）
	 * 
	 * @param key
	 * @param dbIndex
	 * @return
	 */
	public Map<String, String> hgetAll(String key) {
		return hgetAll(key, 0);
	}

	/***
	 * 根据key取得map集合（一对多）
	 * 
	 * @param key
	 * @param dbIndex
	 * @return
	 */
	public Map<String, String> hgetAll(String key, int dbIndex) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		Map<String, String> map = jedis.hgetAll(key);
		jedis.close();
		return map;
	}

	public String hmset(String key, Map<String, String> hash) {
		return hmset(key, hash, 0);
	}

	/**
	 * key-Map<String,String> 存储
	 * 
	 * @param key
	 * @param hash
	 * @param dbIndex
	 *            数据库索引
	 * @return
	 */
	public String hmset(String key, Map<String, String> hash, int dbIndex) {
		if (hash == null) {
			return "";
		}

		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		String ret = jedis.hmset(key, hash);
		jedis.close();
		return ret;
	}

	public String hget(String key, String field) {
		return hget(key, field, 0);
	}

	/**
	 * 
	 * @param key
	 * @param field
	 * @param dbIndex
	 * @return
	 */
	public String hget(String key, String field, int dbIndex) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		String ret = jedis.hget(key, field);
		jedis.close();
		return ret;
	}

	@Override
	public long getCounter(String key) {
		return 0;
	}

	@Override
	public <T> boolean set(String key, T value) {
		return set(key, value, 0);
	}

	/**
	 * 设置key-value
	 * 
	 * @param key
	 * @param value
	 * @param dbIndex
	 * @return
	 */
	public <T> boolean set(String key, T value, int dbIndex) {
		if (value == null) {
			return false;
		}
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		jedis.set(key.getBytes(), toBytes(value));
		jedis.close();
		return true;
	}

	@Override
	public boolean storeCounter(String key, long value) {
		return false;
	}

	@Override
	public boolean remove(String key) {
		return false;
	}

	@Override
	public boolean has(String key) {
		return has(key, 0);
	}

	/**
	 * 判断dbIndex库下是否存在当前key
	 * 
	 * @param key
	 * @param dbIndex
	 * @return 存在 true ,不存在 false
	 */
	public boolean has(String key, int dbIndex) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		boolean ret = jedis.exists(key);
		jedis.close();
		return ret;
	}

	public Long hset(String key, String field, String value) {
		return hset(key, field, value, 0);
	}

	public Long hset(String key, String field, String value, int dbIndex) {
		if (field == null || value == null) {
			return Long.valueOf(-1);
		}
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		Long ret = jedis.hset(key, field, value);
		jedis.close();
		return ret;
	}

	/**
	 * 清空dbIndex 数据
	 * 
	 * @param dbIndex
	 */
	public void flushDB(int dbIndex) {
		ShardedJedis jedis = pool.getResource();
		Collection<Jedis> jedisList = jedis.getAllShards();
		Iterator<Jedis> ite = jedisList.iterator();
		while (ite.hasNext()) {
			Jedis curJedis = ite.next();
			curJedis.select(dbIndex);
			curJedis.flushDB();
		}
		jedis.close();
	}

	public boolean sismember(String key, String member) {
		return sismember(key, member, 0);
	}

	public boolean sismember(String key, String member, int dbIndex) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		boolean ret = jedis.sismember(key, member);
		jedis.close();
		return ret;
	}

	public boolean hexists(String key, String field) {
		return hexists(key, field, 0);
	}

	public boolean hexists(String key, String field, int dbIndex) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		boolean ret = jedis.hexists(key, field);
		jedis.close();
		return ret;
	}

	public Long scard(String key) {
		return scard(key, 0);
	}

	public Long scard(String key, int dbIndex) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		Long ret = jedis.scard(key);
		jedis.close();
		return ret;
	}

	public Long sadd(String key, String... members) {
		return sadd(0, key, members);
	}

	public Long sadd(int dbIndex, String key, String... members) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		Long ret = jedis.sadd(key, members);
		jedis.close();
		return ret;
	}

	public Long zadd(String key, Map<String, Double> scoreMembers) {
		return zadd(0, key, scoreMembers);
	}

	public Long zadd(int dbIndex, String key, Map<String, Double> scoreMembers) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		Long ret = jedis.zadd(key, scoreMembers);
		jedis.close();
		return ret;
	}

	public Double zscore(int dbIndex, String key, String member) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		Double ret = jedis.zscore(key, member);
		jedis.close();
		return ret;
	}

	public Long zcard(String key) {
		return zcard(0, key);
	}

	public Long zcard(int dbIndex, String key) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		Long ret = jedis.zcard(key);
		jedis.close();
		return ret;
	}

	public Long zrem(String key, String... members) {
		return zrem(0, key, members);
	}

	public Long zrem(int dbIndex, String key, String... members) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		Long ret = jedis.zrem(key, members);
		jedis.close();
		return ret;
	}

	public Set<String> zrevrange(String key, int start, int stop) {
		return zrevrange(0, key, start, stop);
	}

	public Set<String> zrevrange(int dbIndex, String key, int start, int stop) {
		ShardedJedis jedis = pool.getResource();
		selectDB(jedis, dbIndex);
		Set<String> ret = jedis.zrevrange(key, start, stop);
		jedis.close();
		return ret;
	}

	public Double zscore(String key, String member) {
		return zscore(0, key, member);
	}

	public static void main(String[] args) {
		JedisPoolConfig redisConfig = new JedisPoolConfig();
		redisConfig.setMaxTotal(1);
		redisConfig.setMaxIdle(1);
		redisConfig.setMaxWaitMillis(3000);
		redisConfig.setTestOnBorrow(false);
		List<JedisShardInfo> shards = new ArrayList<JedisShardInfo>();
		JedisShardInfo info = new JedisShardInfo("192.168.3.105", 6379);
		// info.setPassword( "mcat" );
		shards.add(info);

		RedisCache client = new RedisCache(redisConfig, shards);

		boolean storeRet = client.storeCounter("test_derek", 23);
		boolean hasRet = client.has("bl_5496065647");
		client.remove("bl_5496065647");
		Object obj = client.get("bl_5496065647");
		client.set("bl_5496065648", null);
		boolean hasRet2 = client.has("bl_5496065648");
		Object obj2 = client.get("bl_5496065648");
		System.out.println(hasRet);
		System.out.println(hasRet2);
		System.out.println(obj == null ? "null" : "not null");
		System.out.println(obj2 == null ? "null" : "not null");

		System.out.println(storeRet);
		long getRet = client.getCounter("test_derek");
		System.out.println(getRet);
		long getRet2 = client.getCounter("test_derek2");
		System.out.println(getRet2);
		// long incRet = client.incr("test_derek", 10);
		// System.out.println(incRet);
		// incRet = client.incr("test_derek", -20);
		// System.out.println(incRet);

		client.hset("hello", "world", "231", 3);
		System.out.println(client.hget("hello", "world", 3));
		getRet = client.getCounter("test_derek");
		System.out.println(getRet);
	}

	@Override
	public long incr(String key, long inc) {
		return 0;
	}

	@Override
	public <T> Set<String> keySet() {
		return null;
	}

	@Override
	public int clear() {
		return 0;
	}

	@Override
	public int size() {
		return 0;
	}
}
