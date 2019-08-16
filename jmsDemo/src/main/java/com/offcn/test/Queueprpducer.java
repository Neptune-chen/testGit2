package com.offcn.test;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

public class Queueprpducer {
	
	public static void main(String[] args) {
		try {
			//创建连接工厂
			ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://192.168.188.128:61616");
			//获取连接
			Connection connection = connectionFactory.createConnection();
			//启动连接
			connection.start();
			
			//获取获取session  (参数1：是否启动事务,参数2：消息确认模式)
			Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			
			//创建队列，生产者，消费者
			Queue queue = session.createQueue("test-queue");
			//创建消息生产者
			MessageProducer messageProducer = session.createProducer(queue);
			//创建消息
			TextMessage textMessage = session.createTextMessage("Hello word");
			
			//发送消息
			int i = 1;
			while (i < 1000) {
				messageProducer.send(textMessage);
				System.out.println("发送"+i+"条信息");
				//睡眠1秒
				Thread.sleep(1000);
				i++;
			}
			
			
			//关闭
			messageProducer.close();
			session.close();
			connection.close();
		} catch (JMSException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
