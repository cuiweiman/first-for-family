package com.first.family.mqtt.config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @description: mqtt broker 的连接信息
 * @author: cuiweiman
 * @date: 2023/7/19 16:41
 */
public class MqttBrokerInfo {

    public String tcp;
    public String username;
    public String password;
    public String pubClientId;
    public String subClientId;
    public boolean cleanSession;
    public Integer keepAliveInterval;
    public Integer connectionTimeout;
    public boolean automaticReconnect;

    public MqttBrokerInfo() {
        try (InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("mqtt.properties")) {
            Properties properties = new Properties();
            properties.load(resourceAsStream);
            this.tcp = properties.getProperty("mqtt.tcp");
            this.username = properties.getProperty("mqtt.username");
            this.password = properties.getProperty("mqtt.password");
            this.pubClientId = properties.getProperty("mqtt.pubClientId");
            this.subClientId = properties.getProperty("mqtt.subClientId");
            this.cleanSession = Boolean.parseBoolean(properties.getProperty("mqtt.clean-session"));
            this.keepAliveInterval = Integer.parseInt(properties.getProperty("mqtt.keep-alive-interval"));
            this.connectionTimeout = Integer.parseInt(properties.getProperty("mqtt.connection-timeout"));
            this.automaticReconnect = Boolean.parseBoolean(properties.getProperty("mqtt.automatic-reconnect"));


        } catch (IOException e) {
            FileNotFoundException fileNotFoundException = new FileNotFoundException("mqtt配置文件找不到");
            throw new RuntimeException(fileNotFoundException);
        }
    }


    public String getTcp() {
        return tcp;
    }

    public boolean getCleanSession() {
        return cleanSession;
    }

    public Integer getKeepAliveInterval() {
        return keepAliveInterval;
    }

    public Integer getConnectionTimeout() {
        return connectionTimeout;
    }

    public boolean getAutomaticReconnect() {
        return automaticReconnect;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPubClientId() {
        return pubClientId;
    }

    public String getSubClientId() {
        return subClientId;
    }
}
