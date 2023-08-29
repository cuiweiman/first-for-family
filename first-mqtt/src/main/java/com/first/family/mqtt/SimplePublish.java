package com.first.family.mqtt;

import com.first.family.mqtt.config.MqttBrokerInfo;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.nio.charset.StandardCharsets;

/**
 * @description: 普通TCP连接
 * @author: cuiweiman
 * @date: 2023/7/18 17:48
 */
public class SimplePublish {

    public static void main(String[] args) throws MqttException {
        MqttBrokerInfo mqttBrokerInfo = new MqttBrokerInfo();
        MqttConnectOptions connectOptions = getConnectOptions(mqttBrokerInfo);
        MqttClient mqttClient = getMqttClient(mqttBrokerInfo);
        mqttClient.connect(connectOptions);
        if (!mqttClient.isConnected()) {
            System.out.println(mqttBrokerInfo.getPubClientId() + " 连接失败");
        } else {
            String topic = "gc/media/723473855879";
            String payload = "{\"mac\":\"16383680626961931638368062696193\",\"data\":\"云服务器BIE\"}";
            for (int i = 0; i < 50; i++) {
                mqttClient.publish(topic, payload.getBytes(StandardCharsets.UTF_8), 0, false);
                System.out.println(i);
            }
        }
        if (mqttClient.isConnected()) {
            mqttClient.disconnect();
        }
    }

    public static MqttConnectOptions getConnectOptions(MqttBrokerInfo mqttBrokerInfo) {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setUserName(mqttBrokerInfo.getUsername());
        options.setPassword(mqttBrokerInfo.getPassword().toCharArray());
        options.setCleanSession(mqttBrokerInfo.getCleanSession());
        options.setKeepAliveInterval(mqttBrokerInfo.getKeepAliveInterval());
        options.setConnectionTimeout(mqttBrokerInfo.getConnectionTimeout());
        options.setAutomaticReconnect(mqttBrokerInfo.getAutomaticReconnect());
        return options;
    }

    public static MqttClient getMqttClient(MqttBrokerInfo mqttBrokerInfo) throws MqttException {
        return new MqttClient(mqttBrokerInfo.getTcp(), mqttBrokerInfo.getPubClientId(), new MemoryPersistence());
    }

}
