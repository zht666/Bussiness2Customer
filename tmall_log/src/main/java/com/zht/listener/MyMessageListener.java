package com.zht.listener;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;

import com.zht.mapper.IndexMapper;

//读消息时，整合框架过程当中，需要参与到整合的过程
public class MyMessageListener implements MessageListener {

	@Autowired
	IndexMapper indexMapper;
	
	@Override
	public void onMessage(Message message) {
		//判断是什么消息
		// 消息内容
		TextMessage textMessage = (TextMessage) message;
		try {
			String text = textMessage.getText();
			//根据消息内容执行对应的任务
			indexMapper.log(text);
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
