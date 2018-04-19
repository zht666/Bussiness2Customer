package mq_producer.mq_producer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**队列模式
 * message产生端
 * 消息提供者
 */
public class App {
	public static void main(String[] args) throws JMSException {
		// 发送一个队列模式的消息
		//创建连接工厂
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");//默认使用tcp协议
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		//创建消息会话
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//创建队列，消息对象
		Queue queue = session.createQueue("office-queue");//application是消息对象，消息业务模块
		
		//创建消息内容
		TextMessage textMessage = session.createTextMessage("我渴了，来杯水");//message消息内容本身
				
		//session通过消息对象，创建发送端
		MessageProducer producer = session.createProducer(queue);
		
		//发送消息
		producer.send(textMessage);
		
		//关闭连接
		producer.close();
		session.close();
		connection.close();

	}
}
