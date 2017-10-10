/**
* @author  derek(derek@intoms.com)
* @version 创建时间：2008-9-30 下午02:51:25
* 类说明
*/
package com.hy.cache;

import java.util.Set;

/**
 * Cache的基类
 */
public interface Cache {
	/**
	 * 从cache取某个节点
	 * @param <T>
	 * @param key
	 * @return
	 */
    public <T> T get( String key );
    
	/**
	 * 从cache取计数器的值
	 * @param <T>
	 * @param key
	 * @return
	 */
    public long getCounter( String key );
    
    /**
     * 将某个节点保存到cache中
     * @param <T>
     * @param key
     * @param value
     */
    public <T> boolean set( String key, T value );
    
	/**
	 * 写入计数器的值
	 * @param <T>
	 * @param key
	 * @param value
	 * @return
	 */
    public boolean storeCounter( String key, long value );    
    
    /**
     * 在cache中删除某个节点
     * @param key
     */
    public boolean remove( String key );
    
    /**
     * 是否存在这个主键的节点
     * @param key
     * @return
     */
    public boolean has( String key );
    
    /**
     * 将指定的原子属性增加或者减少一个值。注意：如果原子属性不存在，则增加原子属性并且将值设置为vlaue；如果减少之后小于0，自动复位为0.
     * @param key
     * @param inc
     * @param value 
     * @return
     */
    public long incr(String key, long inc);    
    
    /**
     * 得到能遍历当前cache内所有数据的迭代器
     * @param <T>
     * @return null表示不支持这种操作
     */
    public <T> Set<String> keySet();
    
    /**
     * 清空cache内的数据
     * @return 清除的个数，-1表示不支持这种操作
     */
    public int clear();
    
    /**
     * 返回cache内的元素个数
     * @return
     */
    public int size();
}
 