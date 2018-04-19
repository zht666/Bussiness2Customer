package com.zht.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zht.bean.T_MALL_USER_ACCOUNT;
import com.zht.mapper.LoginMapper;
import com.zht.util.MyRoutingDataSource;

@Service
public class LoginServiceImpl implements LoginService {

	@Autowired
	LoginMapper loginMapper;
	
	@Override
	public T_MALL_USER_ACCOUNT login(T_MALL_USER_ACCOUNT user) {
		//切换为1数据源
		MyRoutingDataSource.setKey("1");
		
		loginMapper.update_test();
		
		return loginMapper.select_user(user);
	}
	
	@Override
	public T_MALL_USER_ACCOUNT login2(T_MALL_USER_ACCOUNT user) {
		//切换为2数据源
		MyRoutingDataSource.setKey("2");
		
		//模拟并发，让数据源2先登录，再登录数据源1
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return loginMapper.select_user(user);
	}

}
