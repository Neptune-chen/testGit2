package com.offcn.test;

import java.io.IOException;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class QueueConsumer {

	public static void main(String[] args) {
		System.out.println("测试git");
		System.out.println("测试分支bate");
		try {
			//创建连接工厂
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.188.128:61616");
			//获取连接
			Connection connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			
			//获取获取session  (参数1：是否启动事务,参数2：消息确认模式)
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//创建队列，消费者
			Queue queue = session.createQueue("test-queue");
			
			//创建消息消费
			MessageConsumer consumer = session.createConsumer(queue);
			//监听消息
			consumer.setMessageListener(new MessageListener() {
				
				@Override
				public void onMessage(Message message) {
					// TODO Auto-generated method stub
					TextMessage textMessage = (TextMessage) message;
					try {
						System.out.println("获取文本内容："+textMessage.getText());
						
						//睡眠2秒
						/*
						 * Thread.sleep(2000); System.out.println("消费一条");
						 */
					} catch (JMSException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
			//等待键盘输入
			System.in.read();	
			//关闭资源
			consumer.close();
			session.close();
			connection.close();	
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
