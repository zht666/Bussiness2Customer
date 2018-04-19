package com.zht.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/** 
* 日期工具类：
*   对日期格式化
*   计算相对于当前日期的前后指定日期
*/
public class MyDateUtil {

	public static void main(String[] args) {
		// 关于日期格式化
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = sdf.format(new Date());// 格式化
		System.out.println(format);

		// 关于日期计算
		Calendar c = Calendar.getInstance();// 获取当前时间
		c.add(Calendar.DATE, 3); // 对当前日期进行加减，正数代表加，负数代表减
		String newDate = sdf.format(c.getTime());// 返回时间
		System.out.println(newDate);

		// c.add(Calendar.DATE, -3);
		// String newDate1 = sdf.format(c.getTime());//返回时间
		// System.out.println(newDate1);
	}

	// 获取相对于当前日期的前后指定日期
	public static Date getMyDate(int day) {
		// 关于日期计算
		Calendar c = Calendar.getInstance();// 获取当前时间

		c.add(Calendar.DATE, day); // 对当前日期进行加减，正数代表加，负数代表减

		return c.getTime();
	}

	// 对时间格式化
	public static String getMyDateString(String myFormat) {
		// 关于日期格式化
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
		String str = sdf.format(new Date());// 格式化
		return str;
	}
}
