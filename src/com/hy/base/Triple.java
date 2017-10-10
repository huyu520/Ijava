package com.hy.base;

import java.io.Serializable;

/**
 * 作用: 
 * @author wb-hy292092
 * @date 2017年7月31日 下午7:55:04
 */
public class Triple<X, Y, Z> implements Serializable ,Comparable<Triple<X, Y, Z>> {

	private static final long serialVersionUID = -6716326114556009978L;

	private X left;

	private Y middle;

	private Z right;

	public Triple() {
	}

	public Triple(X x, Y y, Z z) {
		this.left = x;
		this.middle = y;
		this.right = z;
	}

	public Triple(Triple<X, Y, Z> triple) {
		if (triple != null) {
			this.left = triple.left;
			this.middle = triple.middle;
			this.right = triple.right;
		}
	}
	
	public Triple(Triple<X, Y, Z> triple,X x,Y y, Z z){
		if(triple!= null ){
			this.left = triple.left ;
			this.middle = triple.middle ;
			this.right = triple.right ;
		}else {
			this.left = x ;
			this.middle = y;
			this.right = z; 
		}
	}
	
	public X getLeft() {
		return left;
	}

	public Triple<X, Y, Z> setLeft(X left) {
		this.left = left;
		return this;
	}

	public Y getMiddle() {
		return middle;
	}

	public Triple<X, Y, Z> setMiddle(Y middle) {
		this.middle = middle;
		return this;
	}

	public Z getRight() {
		return right;
	}

	public Triple<X, Y, Z> setRight(Z right) {
		this.right = right;
		return this;
	}

	@SuppressWarnings("hiding")
	public <X, Y, Z> Triple<X, Y, Z> newTriple() {
		return new Triple<>();
	}

	/**
	 * 异或运算 两个操作数的位中，相同则结果为0，不同则结果为1。
	 */
	@Override
	public int hashCode() {
		return (getLeft() == null ? 0 : getLeft().hashCode()) ^ (getMiddle() == null ? 0 : getMiddle().hashCode())
				^ (getRight() == null ? 0 : getRight().hashCode());
	}

	@Override
	public String toString() {
		return new StringBuilder().append(",").append(getLeft()).append(",").append(getMiddle()).append(",")
				.append(getRight()).toString();
	}

	@Override
	public int compareTo(Triple<X, Y, Z> o) {
		return 0;
	}
	
}
