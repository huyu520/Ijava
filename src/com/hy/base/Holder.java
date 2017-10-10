/**
* @author  derek(derek@intoms.com)
* @version 创建时间：2009-1-7 下午07:56:56
* 类说明：保存某个数据类型的值，一般用于在函数中带出返回值
*/
package com.hy.base;

/**
 *ss
 */
public class Holder <T> {
	private T holding;
	
	public Holder() {
	}
	
	public Holder( T value) {
		holding = value;
	}

	public void setValue( T value) {
		holding = value;
	}
	
	public T getValue() {
		return holding;
	}
}
