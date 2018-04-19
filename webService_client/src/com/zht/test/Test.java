package com.zht.test;

public class Test {

	public static void main(String[] args) {

		TestServiceImplService service = new TestServiceImplService();

		TestServiceImpl testServiceImplPort = service.getTestServiceImplPort();
		
		String ping = testServiceImplPort.ping("你好");
		System.out.println(ping);
	}

}
