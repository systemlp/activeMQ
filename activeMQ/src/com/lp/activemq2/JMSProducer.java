package com.lp.activemq2;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSProducer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;// 连接工厂
        Connection connection = null;// 连接
        Session session;// 消息会话
        Destination destination;// 消息发送目的地
        MessageProducer producer;// 消息发送者

        // 创建ActiveMQ连接工厂
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
        try {
            // 创建消息服务器连接
            connection = connectionFactory.createConnection();
            connection.start();// 开启连接
            // 创建和消息服务器的会话，第一个参数表示是否开启事务，第二个参数为消息发送方式
            session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
            // 创建一条点对点等我消息
            destination = session.createTopic("订阅的第一个消息");
            // 创建消息生产者
            producer = session.createProducer(destination);
            // 发送10条消息值服务器中
            for (int i = 0; i < 10; i++) {
                TextMessage textMessage = session.createTextMessage("我使用ActiveMQ消息队列发送的消息：" + i);
                producer.send(textMessage);
                System.out.format("消息队列发送的第%s条消息\n", i);
            }
            // 提交消息发送
            session.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
