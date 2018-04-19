package com.zht.util;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zht.bean.Class1;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class TestJson {

	@Test
	public void testgsonobj() {

		//对象 与 json字符串 互转
		
		Class1 class1 = new Class1(1,"手机");
		System.out.println(class1);
		
		//创建gson对象 
		Gson gson = new Gson();
		String json = gson.toJson(class1);
		System.out.println(json);
		
		Class1 class12 = gson.fromJson(json, Class1.class);
		System.out.println(class12);
	}
	
	@Test
	public void testgsonlist() {
		//集合 与json字符串 互转
		ArrayList<Class1> list = new ArrayList<Class1>();
		list.add(new Class1(1, "电脑办公"));
		list.add(new Class1(2, "服装"));
		System.out.println(list);
		
		
		Gson gson = new Gson();
		String json = gson.toJson(list);
		System.out.println(json);
		
		TypeToken tt = new TypeToken<List<Class1>>() {};
		
		// gson.fromJson(json, Class1.class);
		ArrayList<Class1> list1 = gson.fromJson(json, tt.getType());
		System.out.println(list1);
		
	}
	
	@Test
	public void testjsonlibobj() {
		//对象 与 json字符串 互转
		
		Class1 class1 = new Class1(1,"手机");
		System.out.println(class1);
		
		JSONObject jsonObject = JSONObject.fromObject(class1);
		String str = jsonObject.toString();
		System.out.println(str);
		
		Object bean = JSONObject.toBean(jsonObject, Class1.class);
		System.out.println(bean);
		
	}
	
	@Test
	public void testjsonliblist() {
		//集合 与json字符串 互转
		ArrayList<Class1> list = new ArrayList<Class1>();
		list.add(new Class1(1, "电脑办公"));
		list.add(new Class1(2, "服装"));
		System.out.println(list);
		
		
		JSONArray jsonArray = JSONArray.fromObject(list);
		String str = jsonArray.toString();
		System.out.println(str);
		
		//这里的class是指集合元素的大Class
		ArrayList<Class1> list2 = (ArrayList<Class1>) JSONArray.toCollection(jsonArray, Class1.class);
		System.out.println(list2);
	}
	
	
	@Test
	public void testfastjsonobj() {
		//对象 与 json字符串 互转
		Class1 class1 = new Class1(1,"手机");
		System.out.println(class1);
		
		String str = JSON.toJSONString(class1);
		System.out.println(str);
		
		Class1 cla = JSON.parseObject(str, Class1.class);
		System.out.println(cla);
		
	}
	
	
	@Test
	public void testfastjsonlist() {
		//集合 与json字符串 互转
		ArrayList<Class1> list = new ArrayList<Class1>();
		list.add(new Class1(1, "电脑办公"));
		list.add(new Class1(2, "服装"));
		System.out.println(list);
		
		String str = JSON.toJSONString(list);
		System.out.println(str);
		
		List<Class1> list2 = JSON.parseArray(str, Class1.class);
		System.out.println(list2);
	}
}
