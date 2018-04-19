package mq_consumer.mq_consumer;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**队列模式
 * message消费端
 * 消息消费者
 */
public class App2 {
	public static void main(String[] args) throws Exception {
		consumer2();
	}
	
	public static void consumer2() throws Exception {
		//创建连接
		ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
		Connection connection = connectionFactory.createConnection();
		connection.start();
		
		//创建会话
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		
		//创建消息对象
		Queue queue = session.createQueue("office-queue");
		
		//接收端
		MessageConsumer consumer = session.createConsumer(queue);
		
		// 接收消息
		consumer.setMessageListener(new MessageListener() {//消息监听器，监听mq服务器的变化
			@Override
			public void onMessage(Message message) {
				// 打印结果
				TextMessage textMessage = (TextMessage) message;
				String text = "";
				try {
					text = textMessage.getText();//强制抓异常，担心text出问题
				} catch (JMSException e) {
					e.printStackTrace();
				}
				System.out.println("小丽，听到了 "+text+" 积极的响应了要求。。。");//打印消息
			}
		});
		// 等待接收消息，让虚拟机处在启动并且接受访问的状态
		System.in.read();//如果没有这行代码，监听器创建之后，瞬间消失，这样虚拟中的MessageListener才能从mq上一致得到消息
		
	}
}
