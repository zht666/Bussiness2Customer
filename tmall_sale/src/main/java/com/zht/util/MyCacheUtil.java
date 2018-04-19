package com.zht.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.Jedis;

/**
 * 
* Jedis获取数据工具类
*
 */
public class MyCacheUtil {
	
	/**
	 * 
	* @Title: interKeys
	* @Description: TODO(通过交叉查询生成动态key)
	* @param     参数
	* @return void    返回类型
	* @throws
	 */
	public static String interKeys(String...keys) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		//生成动态的key
		String k0 = "combine";
		for (int i = 0; i < keys.length; i++) {
			//判断key是否只有一个，如果有一个就直接把key值返回，没必要和combine拼接
			if(keys.length == 1) {
				return keys[0];
			}else {
				k0  = k0 + "_" + keys[i];
			}
		}
		
		//判断k0是否存在，如果不存在才把数据放到redis中
		Boolean exists = jedis.exists(k0);
		if(!exists) {
			jedis.zinterstore(k0, keys);
		}
		//关闭jedis
		try {
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return k0;
	}

	/**
	 * 
	* @Title: getList
	* @Description: TODO(通过key在redis中获取list结合)
	* @param @param key
	* @param @param t
	* @param @return    参数
	* @return List<T>    返回类型
	* @throws
	 */
	public static <T> List<T> getList(String key, Class<T> t) {
		List<T> list = new ArrayList<T>();
		
		//第三方数据库调用
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		Set<String> zrange = jedis.zrange(key, 0, -1);//只要会redis命令就会jedis方法
		Iterator<String> iterator = zrange.iterator();
		while(iterator.hasNext()) {
			String skuStr = iterator.next();
			T sku = MyJsonUtil.json_to_object(skuStr, t);
			list.add(sku);
		}
		//关闭jedis
		try {
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//添加分类编号2信息到redis
	public static <T> void setKey(String key, List<T> list) {
		//第三方数据库调用
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {
			e.printStackTrace();
			//记日志，怎么处理业务系统的业务日志，异步并发操作，调用日志系统
		}
		
		//先删除key对应的数据，再向redis中同步数据，这样才能保证同一条数据库数据是同步到redis中，而不是新增
		//在同步redis之前，需要先把key清理掉
		jedis.del(key);
		
		//同步redis 循环遍历list，向redis中添加数据
		for (int i = 0; i < list.size(); i++) {
			jedis.zadd(key, i, MyJsonUtil.object_to_json(list.get(i)));
		}
		
		//关闭jedis
		try {
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	* @Title: if_key
	* @Description: TODO(判断redis中key是否存在)
	* @param @param key
	* @param @return    参数
	* @return boolean    返回类型
	* @throws
	 */
	public static boolean if_key(String key) {
		//第三方数据库调用
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {
			e.printStackTrace();
			//记日志
		}
		//判断redis中key是否存在
		Boolean exists = jedis.exists(key);
		//关闭jedis
		try {
			jedis.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return exists;
	}

}
