package com.zht.factory;

import java.io.InputStream;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MySqlSessionFacory {

	private MySqlSessionFacory() {};
	private static SqlSessionFactory single;

	public static SqlSessionFactory getSqlFactory() {
//		if (null == single) {
//			synchronized (single) {
//				if (null == single) {
					InputStream resourceAsStream = MySqlSessionFacory.class.getClassLoader()
							.getResourceAsStream("mybatis-config.xml");

					SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
					// 配成单例的
					single = builder.build(resourceAsStream);
//				}
//			}
//		}

		return single;
	}
}
