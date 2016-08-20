package com.lp.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

public class JMSConsumer {

    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;
    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;
    private static final String BROKERURL = ActiveMQConnection.DEFAULT_BROKER_URL;

    public static void main(String[] args) {
        ConnectionFactory connectionFactory;// 连接工厂
        Connection connection = null;// 连接
        Session session;// 消息会话
        Destination destination;// 消息发送目的地
        MessageConsumer consumer;// 消息消费者（接收者）

        // 创建ActiveMQ连接工厂
        connectionFactory = new ActiveMQConnectionFactory(USERNAME, PASSWORD, BROKERURL);
        try {
            // 创建消息服务器连接
            connection = connectionFactory.createConnection();
            connection.start();// 开启连接
            // 创建和消息服务器的会话，第一个参数表示是否开启事务，第二个参数为消息发送方式
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
            // 创建一条点对点等我消息
            destination = session.createQueue("我的第一个点对点消息");
            // 创建消息接收者
            consumer = session.createConsumer(destination);
            while (true) {
                TextMessage message = (TextMessage) consumer.receive();
                if (message != null) {
                    System.out.println(message.getText());
                } else {
                    System.out.println("发送者未发送消息");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
