package com.zht.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MyPropertyUtil {

	public static String getProperty(String pro, String key) {
		//Properties工具类目的是为了解析.properties文件的
		Properties properties = new Properties();
		
		//把myUpload.properties文件转换为输入流
		InputStream resourceAsStream = MyPropertyUtil.class.getClassLoader().getResourceAsStream(pro);
		
		try {
			//加载输入流
			properties.load(resourceAsStream);
			if(resourceAsStream != null) {
				//关闭流
				resourceAsStream.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//通过key获取路径值
		String property = properties.getProperty(key);
		
		return property;
	}

}
