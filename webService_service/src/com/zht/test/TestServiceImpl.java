package com.zht.test;

import javax.jws.WebService;

@WebService
public class TestServiceImpl implements TestService {

	@Override
	public String ping(String hello) {
		System.out.println("接口返回调用："+hello);
		return "pong";
	}

}
