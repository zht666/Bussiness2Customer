package com.zht.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.zht.bean.KEYWORDS_T_MALL_SKU;
import com.zht.bean.MODEL_T_MALL_SKU_ATTR_VALUE;
import com.zht.bean.OBJECT_T_MALL_ATTR;
import com.zht.bean.OBJECT_T_MALL_SKU;
import com.zht.bean.T_MALL_SKU_ATTR_VALUE;
import com.zht.service.AttrService;
import com.zht.service.ListService;
import com.zht.util.JedisPoolUtils;
import com.zht.util.MyCacheUtil;
import com.zht.util.MyHttpGetUtil;
import com.zht.util.MyJsonUtil;
import com.zht.util.MyPropertiesUtil;

import redis.clients.jedis.Jedis;

@Controller
public class ListController {

	@Autowired
	AttrService attrService;
	
	@Autowired
	ListService listService;
	
	@RequestMapping("keywords")
	public String keywords(String keywords, ModelMap map) {
		
		//调用keywords关键词查询接口
		String doGet = "";
		String url = MyPropertiesUtil.getMyProperty("ws.properties", "keywords_url")+"?keywords="+keywords;
		try {
			doGet = MyHttpGetUtil.doGet(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<KEYWORDS_T_MALL_SKU> list_sku = MyJsonUtil.json_to_list(doGet, KEYWORDS_T_MALL_SKU.class);
		map.put("list_sku", list_sku);
		map.put("keywords", keywords);
		map.put("gotoLi", "2");
		return "list";
	}
	
	@RequestMapping("goto_list")
	public String goto_list(int flbh2, ModelMap map) {
		
		//flbh2属性的集合
		List<OBJECT_T_MALL_ATTR> list_attr = attrService.get_attr_list(flbh2);
		
		//flbh2商品列表
		List<OBJECT_T_MALL_SKU> list_sku = new ArrayList<OBJECT_T_MALL_SKU>();
		
		//缓存检索
		String key = "class_2_" + flbh2;//根据分类编号2获取key
		list_sku = MyCacheUtil.getList(key, OBJECT_T_MALL_SKU.class);//从redis中查询sku列表
		
		if (list_sku == null || list_sku.size() < 1) {
			//MySQL检索
			list_sku = listService.get_list_by_flbh2(flbh2);
			
			//将检索结果同步到redis
			MyCacheUtil.setKey(key, list_sku);
		}
		
		map.put("list_attr", list_attr);
		map.put("list_sku", list_sku);
		map.put("flbh2", flbh2);
		map.put("gotoLi", "1");
		return "list";
	}
	
	//ajax异步提交参数的方式二：用json方式异步传参
	@RequestMapping("get_list_by_attr")//json传参
	public String get_list_by_attr(MODEL_T_MALL_SKU_ATTR_VALUE get_list_by_attr, int flbh2, ModelMap map) {
		
		//根据属性查询列表的业务
		List<OBJECT_T_MALL_SKU> list_sku = new ArrayList<OBJECT_T_MALL_SKU>();
		
		//缓存检索，到redis查询缓存数据
		List<T_MALL_SKU_ATTR_VALUE> list_attr = get_list_by_attr.getList_attr();//获取属性集合
		//先判断属性检索条件是否为空，如果为空，显示所有分类编号2下的数据，如果不为空，根据属性检索数据
		if (list_attr == null) {
//			list_sku = listService.get_list_by_flbh2(flbh2);//从数据库查询所有sku信息
			//缓存检索，从redis中查询所有sku信息
			String key = "class_2_" + flbh2;//根据分类编号2获取key
			list_sku = MyCacheUtil.getList(key, OBJECT_T_MALL_SKU.class);//从redis中查询sku列表
		} else {
			String[] keys = new String[list_attr.size()];
			for (int i = 0; i < list_attr.size(); i++) {
				keys[i] = "attr_" + flbh2 + "_" + list_attr.get(i).getShxm_id() + "_" + list_attr.get(i).getShxzh_id();//拼接keys
			}
			
			//交叉检索，返回生成的key
			String interKeys = MyCacheUtil.interKeys(keys);//交叉查询生成动态key
			list_sku = MyCacheUtil.getList(interKeys, OBJECT_T_MALL_SKU.class);//从redis中查询sku列表
			
			if(list_sku == null || list_sku.size() < 1) {
				//MySQL检索，当前交叉检索的结果
				list_sku = listService.get_list_by_attr(get_list_by_attr.getList_attr(),flbh2);
				
				//将检索结果同步到redis
				for (int i = 0; i < list_attr.size(); i++) {
					String key = keys[i]; //获取每个属性id：attr_28_1_2
					
					//判断redis中key是否存在
					boolean if_key = MyCacheUtil.if_key(key);
					
					//如果key不存在才向redis中插入数据
					if(!if_key) {
						//根据属性id，查询出所有属性值集合
						//循环属性值，拼接出attr的key
						//将key所对应的sku集合，刷新到redis中
						List<T_MALL_SKU_ATTR_VALUE> list_attr_for_redis = new ArrayList<T_MALL_SKU_ATTR_VALUE>();
						List<OBJECT_T_MALL_SKU> list_sku_for_redis = new ArrayList<OBJECT_T_MALL_SKU>();
						T_MALL_SKU_ATTR_VALUE attr_value = new T_MALL_SKU_ATTR_VALUE();
						attr_value.setShxm_id(list_attr.get(i).getShxm_id());//设置属性名id
						attr_value.setShxzh_id(list_attr.get(i).getShxzh_id());//设置属性值id
						list_attr_for_redis.add(attr_value);//把单个属性名id和属性值id放到list集合中
						list_sku_for_redis = listService.get_list_by_attr(list_attr_for_redis, flbh2);//通过一对单个属性名id和属性值id到数据库查询sku列表信息
						//再根据属性和属性值查询出对应的sku集合
						//attr的key和sku的集合循环插入到redis
						MyCacheUtil.setKey(key, list_sku_for_redis);
					}
				}
			}

		}
		
		map.put("list_sku", list_sku);
		
		return "skuList";
	}
	
	//添加属性信息到redis
	public void addJedisAttr(MODEL_T_MALL_SKU_ATTR_VALUE get_list_by_attr, int flbh2, List<OBJECT_T_MALL_SKU> list_sku) {
		Jedis jedis = null;
		try {
			jedis = JedisPoolUtils.getJedis();
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<T_MALL_SKU_ATTR_VALUE> list_attr = get_list_by_attr.getList_attr();
		if (list_attr != null) {
			for (int i = 0; i < list_sku.size(); i++) {
				OBJECT_T_MALL_SKU sku = list_sku.get(i);
				for (int j = 0; j < list_attr.size(); j++) {
					String k1 = "attr_" + flbh2 + "_" + list_attr.get(j).getShxm_id() + "_" + list_attr.get(j).getShxzh_id();
					jedis.zadd(k1, j, MyJsonUtil.object_to_json(sku));
				}
			}
		}
	}
	
	//ajax异步提交参数的方式一：用数组方式异步传参
//	@RequestMapping("get_list_by_attr")//数组传参：异步提交如果是数组的话参数后面要加中括号[]
//	public String get_list_by_attr(@RequestParam("param_array[]") String[] param_array) {
//		
//		//如果是字符串数组，需要切割字符串，并且转化类型
//		
//		return "skuList";
//	}
	
}
