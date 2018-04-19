package com.zht.server;

public class TestServerImpl implements TestServer {

	@Override
	public String ping(String hello) {
		System.out.println("cxf接口调用："+hello);
		return "pong";
	}

}
