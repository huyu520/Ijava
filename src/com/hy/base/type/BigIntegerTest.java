package com.hy.base.type;

import java.math.BigInteger;
import java.util.Arrays;

/**
 * 作用:BigInteger 测试
 * 
 * @author wb-hy292092
 *         在用C或者C++处理大数时感觉非常麻烦，但是在Java中有两个类BigInteger和BigDecimal分别表示大整数类和大浮点数类，至于两个类的对象能表示最大范围不清楚，理论上能够表示无线大的数，只要计算机内存足够大。
 *         这两个类都在java.math.*包中，因此每次必须在开头处引用该包。
 * @date 2017年8月2日 上午10:33:29
 */
public class BigIntegerTest {
	public static void main(String[] args) {
		/*
		 * BigInteger big_1 = new BigInteger("999"); BigInteger big_2 = new
		 * BigInteger("50"); System.out.println("两者之和:" + big_1.add(big_2));
		 * System.out.println("两者之差:" + big_1.subtract(big_2));
		 * System.out.println("两者之积:" + big_1.multiply(big_2));
		 * System.out.println("两者之除:" + big_1.divide(big_2));
		 * 
		 * //返回商和余数的数组 BigInteger [] bgs = big_1.divideAndRemainder(big_2);
		 * System.out.println("divideAndRemainder:" + Arrays.asList(bgs));
		 * System.out.println("商:" + bgs[0]); System.out.println("余数:" +
		 * bgs[1]);
		 */

		System.out.println(calBitsValue(3));

	}

	/**
	 * 计算bit的值 计算成2的多少次方的大整形
	 *
	 * @param bit
	 * @return
	 */
	public static BigInteger calBitsValue(int bit) {

		if (bit < 0) {
			return new BigInteger("0");
		}

		BigInteger two = BigInteger.valueOf(21);
		BigInteger bitValue = two.pow(bit);

		return bitValue;
	}
}
