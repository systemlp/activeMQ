package com.lp.activemq2;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class TextMessageListner implements MessageListener {

    private TextMessageListner() {

    }

    private static class TextMessageListnerFactory {
        public static final TextMessageListner LISTNER = new TextMessageListner();
    }

    public static TextMessageListner getInstance() {
        return TextMessageListnerFactory.LISTNER;
    }

    @Override
    public void onMessage(Message message) {
        try {
            System.out.println("订阅者二收到的消息：\t" + ((TextMessage) message).getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

}
