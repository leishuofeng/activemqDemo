package com.study.second;

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
    public void receiveTextMessage(){
        try{
            // 创建连接工厂
            factory = new ActiveMQConnectionFactory(ActivemqPropertiesTools.getValue("activemqUser"),
                    ActivemqPropertiesTools.getValue("activemqPassword"),
                    ActivemqPropertiesTools.getValue("activemqConnectAddress"));
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(false,Session.CLIENT_ACKNOWLEDGE);
            destination = session.createQueue("001mq");
            consumer = session.createConsumer(destination);
            // 注册监听器，注册成功后，队列中的消息变化会自动触发监听器代码。接收消息并处理
            consumer.setMessageListener(new MessageListener() {
                @Override
                public void onMessage(Message message) {
                    try{
                        // acknowledge 代表确认方法，即 消费者已经收到消息，确定后MQ删除对应的消息
                        message.acknowledge();
                        ObjectMessage om = (ObjectMessage) message;
                        System.out.println(om.getObject());
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            });
            System.in.read();
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
    }

    public static void main(String[] args) {
        TextConsumer textConsumer = new TextConsumer();
    }
}
