package com.study.first;

import com.study.util.ActivemqPropertiesTools;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TextConsumer {
    ConnectionFactory factory;

    Connection connection;

    Session session;

    Destination destination;

    MessageConsumer consumer;
    Message message;
    String resultCode;
    public String receiveTextMessage(){
        try{
            // 创建连接工厂
            factory = new ActiveMQConnectionFactory(ActivemqPropertiesTools.getValue("activemqUser"),
                    ActivemqPropertiesTools.getValue("activemqPassword"),
                    ActivemqPropertiesTools.getValue("activemqConnectAddress"));
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            destination = session.createQueue("001mq");
            consumer = session.createConsumer(destination);
            message = consumer.receive();
            resultCode = ((TextMessage)message).getText();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(consumer != null){
                try {
                    consumer.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(session != null){
                try {
                    session.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if(connection != null){
                try {
                    connection.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return resultCode;
    }

    public static void main(String[] args) {
        TextConsumer textConsumer = new TextConsumer();
        System.out.println(textConsumer.receiveTextMessage());
    }
}
