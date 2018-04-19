package com.zht.util;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyRoutingDataSource extends AbstractRoutingDataSource{

	//线程工具类ThreadLocal，避免高并发出现的问题
	private static ThreadLocal<String> key = new ThreadLocal<String>();
	
	@Override
	protected Object determineCurrentLookupKey() {
		// TODO Auto-generated method stub
		return this.key.get();
	}

	public static String getKey() {
		return key.get();
	}

	public static void setKey(String key_in) {
		key.set(key_in);
	}

}
