package com.zht.test;

import javax.xml.ws.Endpoint;

public class TestPublish {

	public static void main(String[] args) {

		TestService ws = new TestServiceImpl();
		
		Endpoint.publish("http://192.168.1.111:1234/ws", ws);
	}

}
