package com.study.first;

import com.study.util.ActivemqPropertiesTools;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.ConnectionFactory;

public class TextProducer {
    // 连接工厂
    ConnectionFactory factory;
    public void sendTextMessage(String datas){
       factory = new ActiveMQConnectionFactory(ActivemqPropertiesTools.getValue("activemqUser"),
               ActivemqPropertiesTools.getValue("activemqPassword"),
               ActivemqPropertiesTools.getValue("activemqConnectAddress"));
    }

}
