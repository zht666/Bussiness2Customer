package com.zht.server;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;
import com.zht.bean.T_MALL_USER_ACCOUNT;
import com.zht.service.LoginService;
import com.zht.util.MyRoutingDataSource;

public class LoginServerImpl implements LoginServer {

	@Autowired
	LoginService loginService;
	
	@Override
	@Path("login") //访问到方法要经历的path
	@GET //访问到方法一般用get协议
	@Consumes("application/x-www-form-urlencoded") //访问到方法的参数类型
	@Produces("application/json") //返回值类型
	public String login(@BeanParam T_MALL_USER_ACCOUNT user) {
		MyRoutingDataSource.setKey("2");//添加事务之后，在这里切换数据源
		T_MALL_USER_ACCOUNT select_user = loginService.login(user);
		
		Gson gson = new Gson();
		return gson.toJson(select_user);
	}
	
	@Override
	@Path("login2") //访问到方法要经历的path
	@GET //访问到方法一般用get协议
	@Consumes("application/x-www-form-urlencoded") //访问到方法的参数类型
	@Produces("application/json") //返回值类型
	public String login2(@BeanParam T_MALL_USER_ACCOUNT user) {
		T_MALL_USER_ACCOUNT select_user = loginService.login2(user);
		
		Gson gson = new Gson();
		return gson.toJson(select_user);
	}


}
