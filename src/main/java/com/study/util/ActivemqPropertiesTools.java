package com.study.util;

import java.io.IOException;
import java.util.Properties;

public class ActivemqPropertiesTools {
    private static Properties p = new Properties();

    static{
        try {
            p.load(ActivemqPropertiesTools.class.getClassLoader().getResourceAsStream("activemq.properties"));
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static String getValue(String key){
        return p.getProperty(key);
    }
}
