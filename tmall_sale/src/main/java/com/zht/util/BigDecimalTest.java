package com.zht.util;

import java.math.BigDecimal;

/**
 * 
 * 测试 BigDecimal 所有与金融和钱有关的都需要用BigDecimal
 * 
 * 原则： double 和 float 一定不能用 BigDecimal 可以用 int 和 long 可用可不用
 *
 */
public class BigDecimalTest {

	public static void main(String[] args) {
		// 初始化
		BigDecimal b1 = new BigDecimal("0.02");
		BigDecimal b2 = new BigDecimal(0.02d); // double采用幂函数转分数算法
		BigDecimal b3 = new BigDecimal(0.02f); // double越大 float越小
		System.out.println(b1);
		System.out.println(b2);
		System.out.println(b3);

		// 比较大小
		int compareTo = b2.compareTo(b3);
		System.out.println(compareTo); // 返回结果会有三种情况：-1 0 1

		// 运算
		BigDecimal b6 = new BigDecimal("6");
		BigDecimal b7 = new BigDecimal("7");

		BigDecimal add = b6.add(b7);// 加法
		System.out.println(add);

		BigDecimal subtract = b6.subtract(b7);
		System.out.println(subtract);// 减法

		BigDecimal multiply = b6.multiply(b7);
		System.out.println(multiply);// 乘法

		// 取舍
		// 计算除法时，要设置小数，如果不设置在出现无限循环小数时会报异常
		// 第二个参会代表保留几位小数，BigDecimal.ROUND_HALF_DOWN 代表保留策略：四舍五入
		BigDecimal divide = b6.divide(b7, 4, BigDecimal.ROUND_HALF_DOWN);
		System.out.println(divide);// 除法

		/*
		 * BigDecimal.setScale()方法用于格式化小数点 setScale(1)表示保留一位小数，默认用四舍五入方式
		 * setScale(1,BigDecimal.ROUND_DOWN)直接删除多余的小数位，如2.35会变成2.3
		 * setScale(1,BigDecimal.ROUND_UP)进位处理，2.35变成2.4
		 * setScale(1,BigDecimal.ROUND_HALF_UP)四舍五入，2.35变成2.4
		 * setScaler(1,BigDecimal.ROUND_HALF_DOWN)四舍五入，2.35变成2.3，如果是5则向下舍
		 */
		BigDecimal add2 = b2.add(b3);
		BigDecimal setScale = add2.setScale(4, BigDecimal.ROUND_HALF_DOWN);
		System.out.println(setScale);

	}

}
