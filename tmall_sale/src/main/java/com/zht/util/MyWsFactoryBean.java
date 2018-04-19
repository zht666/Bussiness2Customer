package com.zht.util;

import java.util.HashMap;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.ws.security.wss4j.WSS4JOutInterceptor;
import org.apache.wss4j.dom.handler.WSHandlerConstants;
import org.springframework.beans.factory.FactoryBean;

public class MyWsFactoryBean<T> implements FactoryBean<T>{

	private String url;
	private Class<T> t;
	
	public static <T> T getMyWs(String url, Class<T> t) {
		JaxWsProxyFactoryBean jwfb = new JaxWsProxyFactoryBean();//ws代理工厂类
		jwfb.setAddress(url);//设置地址
		jwfb.setServiceClass(t);//设置接口的类型
		
		//加入安全协议
		if (t.getSimpleName().equals("TestServer")) {
			HashMap<String, Object> hashMap = new HashMap<String, Object>();
			hashMap.put(WSHandlerConstants.ACTION, WSHandlerConstants.USERNAME_TOKEN);
			hashMap.put(WSHandlerConstants.PASSWORD_TYPE, "PasswordText");
			hashMap.put("user", "username");
			hashMap.put(WSHandlerConstants.PW_CALLBACK_CLASS, MyCallback.class.getName());
			
			WSS4JOutInterceptor wss4jOutInterceptor = new WSS4JOutInterceptor(hashMap);
			jwfb.getOutInterceptors().add(wss4jOutInterceptor);
		}
		
		T bean = (T)jwfb.create();
		
		return bean;
	}

	@Override
	public T getObject() throws Exception {
		return getMyWs(url, this.t);
	}

	@Override
	public Class<?> getObjectType() {
		return this.t;
	}

	@Override
	public boolean isSingleton() {
		return false;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Class<T> getT() {
		return t;
	}

	public void setT(Class<T> t) {
		this.t = t;
	}
	
	
	

}
