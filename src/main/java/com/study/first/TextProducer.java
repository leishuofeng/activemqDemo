package com.study.first;

import com.study.util.ActivemqPropertiesTools;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class TextProducer {
    // 连接工厂
    ConnectionFactory factory;

    // 连接
    Connection connection;

    // 会话
    Session session;

    // 目的地
    Destination destination;

    // 消息发送者
    MessageProducer producer;

    //文本消息
    TextMessage message;
    public void sendTextMessage(String datas){
        try {
            // 创建连接工厂
            factory = new ActiveMQConnectionFactory(ActivemqPropertiesTools.getValue("activemqUser"),
                    ActivemqPropertiesTools.getValue("activemqPassword"),
                    ActivemqPropertiesTools.getValue("activemqConnectAddress"));
            // 创建链接 通过 工厂
            connection = factory.createConnection();
            // 开启连接
            connection.start();
            // 通过连接创建会话对象  参数一：是否支持事务    参数二：表示如何确认消息的处理
            // int AUTO_ACKNOWLEDGE = 1;        自动确认消息，消息的消费者处理消息后，自动确认。常用，商业开发不推荐
            // int CLIENT_ACKNOWLEDGE = 2;      客户端手动确认消息，消费的消费者处理后，必须手工确认
            // int DUPS_OK_ACKNOWLEDGE = 3;     有副本的客户端手动确认
            // 一个消息可以多次处理
            // 可以降低Session的消耗，在可以容忍重复消息时使用（不推荐使用）
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            // 创建目的地。参数是目的地名称，是目的地的唯一标记
            destination = session.createQueue("001mq");
            // 通过会话对象，创建消息的发送者procedure
            // 创建的消息发送者，发送的消息一定到指定的目的地中
            // 创建producer的时候，可以不提供目的地。在发送消息的时候提供目的地。
            producer = session.createProducer(destination);
            // 创建文本消息对象，作为具体数据内容的载体
            message = session.createTextMessage(datas);
            producer.send(message);
            System.out.println("消息已发送");
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(producer != null){
                try {
                    producer.close();
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
    }

    public static void main(String[] args) {
        TextProducer textProducer = new TextProducer();
        textProducer.sendTextMessage("测试消息002");
    }
}
